package br.com.builders.api.integration.cliente;

import br.com.builders.api.cliente.adapter.jpa.ClienteQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class TestsBase {

    @Autowired
    protected ClienteQueryRepository clienteQueryRepository;

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String getPath() {
        return "http://localhost:" + port + "/clientes";
    }

    protected String getPath(String... rest) {
        try {
            return getPath() + "/" + Arrays.asList(rest).stream().map(Object::toString).collect(Collectors.joining("/"));
        } catch (Exception e) {
            return getPath();
        }
    }

}
