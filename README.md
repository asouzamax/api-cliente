# Modelo Arquitetural

Este projeto tem como objetivo demonstrar uma implementação da visão arquitetural de software baseada no padrão [Clean Arch](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

![clean-arch flow](/readme/resource/img/clean-arq.png?raw=true)

### 1- Regras de Dependência:

Como mostra a figura acima, a arquitetura deve ser muito explícita com relação as camadas existentes. Os circulos mais internos são as políticas e regras de negocio. Os circulos mais externos são representados pelos mecanismos de interação com as camadas internas, conforme abaixo:

* **Entidades:** São os objetos de negócios do aplicativo. Eles não devem ser afetados por nenhuma alteração externa devendo conter códificação estável e coesa. Podem ser POJOs, objetos com métodos ou mesmo estruturas de dados.
* **Casos de uso:** Implementa e encapsula todas as regras de negócio.
* **Adaptadores de interface:** Converte e apresenta dados para o caso de uso e para as camadas de entidade. Ports and Adapters concept.
* **Frameworks e drivers:** contêm quaisquer frameworks ou ferramentas necessárias para execução do aplicativo.

### 2 - Metodologias Adotadas

* **D.D.D** - Domain Driven Design - Metodologia utilizada para quebrar e segregar domínios de negócio([Bounded Context](https://martinfowler.com/bliki/BoundedContext.html)).
* **S.O.L.I.D** - [Principios clean code para implementação de software](https://blog.cleancoder.com/uncle-bob/2020/10/18/Solid-Relevance.html). 

### 3 - Techs e stacks:

* Java 11
* SpringBoot para exposição da API
* Maven
* Banco de dados Teste. Console H2 habilitado
* Banco de dados Produção - PostgreSQL
* Flyway para migrations
* Lombok para diminuir verbosidade das classes
* Model Mapper e [MapperConstruct](https://mapstruct.org/documentation/installation/) para evitar o boilerplate entre os representation e domain models [java-faker](http://modelmapper.org/)
* Testes unitários e ou de integração com banco (JUnit e Mockito)
* Valores dinâmicos para testes com [java-faker](https://java-faker.herokuapp.com)
* Swagger para documentação dos endpoints em http://localhost:8080/api/swagger-ui.html
* Handlers para validação e erros de negocio (Básico)
* DockerFile para build do projeto.
* Docker-compose para rodar o projeto.
* Cobertura de código com Jacoco. Relatório pode ser visto em: target/site/jacoco/index.html.
