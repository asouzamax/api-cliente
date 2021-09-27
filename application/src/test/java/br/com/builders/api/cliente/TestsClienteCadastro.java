package br.com.builders.api.cliente;

import br.com.builders.api.cliente.adapter.jpa.ClienteQueryRepository;
import br.com.builders.api.cliente.app.SpringAppApplication;
import br.com.builders.api.cliente.handle.Error;
import br.com.builders.api.cliente.representation.model.ClienteDTO;
import com.github.javafaker.Faker;
import junit.framework.TestSuite;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringAppApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TestsClienteCadastro {

    @Autowired
    private ClienteQueryRepository clienteQueryRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public static junit.framework.Test suite() {
        return new TestSuite ( TestsClienteCadastro.class );
    }

    private String getPath() {
        return "http://localhost:" + port + "/cliente";
    }

    @Test
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
    void deveCadastrarNovoCliente() {
        final Faker faker = new Faker();
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .nome (faker.name ().firstName ())
                .ultimoNome (faker.name ().lastName ())
                .idade (String.valueOf (faker.number ().randomDigit ()))
                .build();
        var response = restTemplate.postForEntity(getPath(), clienteDTO, ClienteDTO.class);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody().getUuid ());
    }

    @Test
    void naoDeveCadastraClienteComNomeJaCadastrado() {
        var cliente= clienteQueryRepository.findAll().stream ().findFirst ();
        var response = restTemplate.postForEntity(getPath(), cliente, Error.class);
        assertNotNull(response);
        assertTrue(
                response.getBody().getStatus().equals(HttpStatus.BAD_REQUEST)
                        && response.getBody().getTitulo().contains("Cliente já está cadastrado")
        );
    }

}
