# Solução do Desafio Backend Encurtador de URLs - TDS Company
[Desafio TDS Company](https://github.com/tdscompany/desafio-de-back-end-encurtador-de-url)

Aplicação backend que encurta e gerencia URLs. O objetivo é receber um link e transformá-lo em outro mais curto, mais fácil de lembrar e que também seja possível saber a quantidade de acessos.

## Sumário

- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos-para-executar-a-aplicação)
- [Instalação](#instalação)
- [Como Usar](#como-usar)
- [Endpoints](#endpoints)
- [Contato](#contato)

## Funcionalidades

- Cadastrar URL e retornar uma encurtada;
- Ser redirecionado para a URL cadastrada ao acessar a URL encurtada;
- Exibir estatisticas de acesso da url encurtada(média de acessos por dia e total geral).


## Tecnologias Utilizadas

- Java
- Spring Boot
- Swagger
- JUnit
- Mockito

## Pré-requisitos para executar a aplicação

- JDK 11+
- Maven
- Banco de Dados PostgreSQL

## Instalação

Passos para instalar e configurar a aplicação localmente:

1. Clone o repositório:
    ```sh
    git clone https://github.com/leticiarose/desafio-backend-encurtador-de-url


2. Navegue até o diretório do projeto:
    ```sh
    cd challenge-backend-url-shortener
    ```
    
3. Instale as dependências:
    ```sh
    mvn clean install
    ```

4. Informe suas credenciais de banco de dados no arquivo `application.properties`.

## Como Usar

Instruções para rodar o projeto:

1. Inicie a aplicação:
    ```sh
    mvn spring-boot:run
    ```
2. Acesse a aplicação no navegador:
    ```
    http://localhost:8080
    ```


## Endpoints

1. Acesse o Swagger por meio do navegador: http://localhost:8080/swagger-ui.html

Ou 

### Salva a URL Original e cria uma nova URL encurtada:
Método HTTP: `POST`
<br>
Endpoint: `/shorten`

**Exemplo de corpo da requisição:**
```json
{
    "urlComplete": "https://github.com/leticiarose/desafio-backend-encurtador-de-url"
}

```
### Exibe as estatisticas de acesso a URL encurtada:
Método HTTP: `GET`
<br>
Endpoint: `/shorten/access/{urlShort}`

### Direciona para a URL original:
Método HTTP: `GET`
<br>
Endpoint: `/{urlShort}` 

## Contato
- Nome: Letícia Lopes
- Email: leticia.rosedesantana@gmail.com
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/leticiarose/)

