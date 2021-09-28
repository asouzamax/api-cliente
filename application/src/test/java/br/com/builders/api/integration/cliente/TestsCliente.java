package br.com.builders.api.integration.cliente;

import br.com.builders.api.cliente.app.SpringAppApplication;
import br.com.builders.api.cliente.handle.Error;
import br.com.builders.api.cliente.representation.model.ClienteDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.javafaker.Faker;
import junit.framework.TestSuite;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringAppApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestsCliente extends TestsBase{

    private static junit.framework.Test suite() {
        return new TestSuite ( TestsCliente.class );
    }

    @Test
    @Order (1)
    void deveValidarCamposClienteCadastro() {
        deveValidarCamposObrigatoriosCliente ();
        deveValidarCampoNomeTamanhoClienteCadastro();
    }

    private void deveValidarCamposObrigatoriosCliente() {
        final var response = restTemplate.postForEntity(getPath(), new ClienteDTO(), Error.class);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertTrue(
                response.getBody().getCampos().stream().allMatch(
                        campo -> campo.getDescricao().contains("obrigatório")
                )
        );
    }

    private void deveValidarCampoNomeTamanhoClienteCadastro() {
        final var cliente = new ClienteDTO ();
        cliente.setNome (StringUtils.leftPad ("0", 129));
        var response = restTemplate.postForEntity (getPath (), cliente, Error.class);
        assertNotNull (response);
        assertNotNull (response.getBody ());
        assertEquals (response.getStatusCode (), HttpStatus.BAD_REQUEST);
        response.getBody ().getCampos ().stream ().filter (
                campo -> campo.getNome ().equals ("nome")
                        && campo.getDescricao ().contains ("Tamanho inválido")
        ).findFirst ().orElseThrow (() -> new AssertionError ());

    }

    @Test
    @Order (2)
    void deveCadastrarNovoCliente() {
        final Faker faker = new Faker();
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .nome (faker.name ().firstName ())
                .ultimoNome (faker.name ().lastName ())
                .idade (faker.number ().randomDigit ())
                .build();
        var response = restTemplate.postForEntity(getPath(), clienteDTO, ClienteDTO.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody().getUuid ());
    }

    @Test
    @Order (3)
    void naoDeveCadastraClienteComNomeJaCadastrado() {
        var cliente= clienteQueryRepository.findAll().stream ().findFirst ();
        var response = restTemplate.postForEntity(getPath(), cliente, Error.class);
        assertNotNull(response);
        assertTrue(
                response.getBody().getStatus().equals(HttpStatus.BAD_REQUEST)
                        && response.getBody().getTitulo().contains("Cliente já está cadastrado")
        );
    }

    @Test
    @Order (4)
    void naoDeveAtualizarClienteNaoEncontrado() {
        Faker faker = new Faker();
        final var cliente = ClienteDTO.builder()
                .nome (faker.funnyName ().name ())
                .ultimoNome (faker.name ().lastName ())
                .idade (faker.number ().randomDigit ())
                .build();

        cliente.setUuid (faker.chuckNorris().fact());
        var response = restTemplate.exchange(getPath(), HttpMethod.PUT, new HttpEntity<> (cliente), Error.class);
        assertTrue(response.getBody().getStatus().equals(HttpStatus.NOT_FOUND)
                && response.getBody().getTitulo().contains("Cliente não encontrado"));
    }

    @Test
    @Order (5)
    void deveAtualizarUmCliente() {
        Faker faker = new Faker();
        final var nome = faker.name().firstName ();
        final var ultimoNome = faker.name().lastName ();

        var cliente= clienteQueryRepository.findAll().stream ().findFirst ();

        final var clienteModificado = new ClienteDTO();
        if(cliente.isPresent ()){

            clienteModificado.setUuid (cliente.get ().getUuid ());
            clienteModificado.setNome(nome);
            clienteModificado.setUltimoNome (ultimoNome);
            clienteModificado.setIdade (cliente.get ().getIdade());

        }
        var response = restTemplate.exchange(getPath(), HttpMethod.PUT, new HttpEntity<> (clienteModificado), ClienteDTO.class);
        assertTrue(response.getStatusCode ().equals(HttpStatus.OK) && response.getBody().getNome().equals(nome));
    }

    @Test
    @Order (6)
    void deveRecuperarTodosClientes() {
        final var response = restTemplate.getForEntity(super.getPath(), JsonNode.class);
        assertNotNull(response);
        assertNotNull(response.getBody ());
        assertEquals(response.getBody ().get ("totalItems").asInt (), 1);
    }

    @Test
    @Order (7)
    void deveRecuperarSomenteUmCliente() {
        var cliente= clienteQueryRepository.findAll().stream ().findFirst ();
        String id = null;
        if(cliente.isPresent ()){
            id = cliente.get ().getUuid ();
        }
        final var response = restTemplate.getForEntity(getPath("{id}"), JsonNode.class, id);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().get("uuid").asText (), id);
    }

    @Test
    @Order (8)
    void deveRetornarErroQuandoBuscarClienteInexistente() {
        final var id = "identificador";
        final var response = restTemplate.getForEntity(getPath("{id}"), Error.class, id);
        assertNotNull(response);
        assertTrue(
                response.getBody().getStatus().equals(HttpStatus.NOT_FOUND)
                        && response.getBody().getTitulo().contains("Cliente não encontrado")
        );
    }

    @Test
    @Order (9)
    void deveExcluirUmCliente() {
        var cliente= clienteQueryRepository.findAll().stream ().findFirst ();
        String uuid = null;
        if(cliente.isPresent ()){
            uuid = cliente.get ().getUuid ();
        }

        final var response = restTemplate.exchange(getPath("{id}"), HttpMethod.DELETE, null, ResponseEntity.class, uuid);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);

        final var clienteNotFound = clienteQueryRepository.findByUuid(uuid);
        assertFalse(clienteNotFound.isPresent());
    }

    @Test
    @Order (10)
    void naoDeveExcluirClienteNaoEncontrado() {
        final var id = "identificador";
        final var response = restTemplate.exchange(getPath("{id}"), HttpMethod.DELETE, null, Error.class, id);
        assertNotNull(response);
        assertTrue(
                response.getBody().getStatus().equals(HttpStatus.NOT_FOUND)
                        && response.getBody().getTitulo().contains("Cliente não encontrado")
        );
    }

}
