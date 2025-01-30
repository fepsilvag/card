# Card

Este sistema foi criado para a prova técnica da empresa Hyperativa

## Índice

- [Enunciado](#enunciado)
- [Requisitos](#requisitos)
- [Execução](#execução)
- [Testes Unitários e de Integração](#testes-unitários-e-de-integração)
- [Collection Postman](#collection-postman)
- [Banco de Dados](#banco-de-dados)

## Enunciado

O enunciado desse problema se encontra no arquivo [enunciado.md](./doc/enunciado.md) no diretório `doc` do projeto.

## Requisitos

- [Git](https://git-scm.com/downloads)
- [Docker](https://docs.docker.com/get-docker/)
- [JDK 17 ou superior](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3.8.3 ou superior](https://maven.apache.org/download.cgi)
- [IntelliJ IDEA (ou outra IDE da sua escolha)](https://www.jetbrains.com/pt-br/idea/download/)

## Execução

Para executar o projeto, siga os passos abaixo:

1. Clone o repositório
```bash
git clone https://github.com/fepsilvag/card.git
```

2. Acesse o diretório do projeto
```bash
cd card
```

3. Execute o comando abaixo para criar a imagem Docker
```bash
docker compose up -d
```

4. Execute o comando abaixo para buildar o projeto (este projeto utiliza API-FIRST, então é necessário compilar antes para gerar as classes)
```bash
mvn clean compile
```

5. Execute o comando abaixo para rodar o projeto
```bash
mvn spring-boot:run
```

## Testes Unitários e de Integração

1. Para rodar os testes unitários e de integração, execute o comando abaixo:
```bash
mvn clean integration-test
```

2. Isso irá gerar um relatório de cobertura de testes no diretório `target/site/jacoco/index.html`

## Collection Postman

A collection do Postman para testar a API se encontra no diretório `doc` do projeto.

- [Collection Postman](./doc/Card.postman_collection.json)
- [Environment Postman](./doc/Card.postman_environment.json)

## Banco de Dados

Projeto do DBeaver para visualizar o banco de dados se encontra no diretório `doc` do projeto.

- [Projeto DBeaver](./doc/card.dbeaver-data-sources)