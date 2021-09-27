package br.com.builders.api.cliente.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"br.com.builders"})
@EntityScan({"br.com.builders.api.cliente.entity.jpa", "br.com.builders.domain.entity"})
@EnableJpaRepositories("br.com.builders.api.cliente.adapter.jpa")
public class SpringAppApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringAppApplication.class, args);
    }

}
