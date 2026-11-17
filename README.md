# Spring Boot + LangChain4j + OpenAI Integration

A sample Spring Boot REST API application that integrates
**LangChain4j** with **OpenAI**. It exposes endpoints for checking
application health and sending prompts to an OpenAI chat model.

## Features

-   Spring Boot REST APIs
-   LangChain4j OpenAI integration (`langchain4j-open-ai`)
-   Configurable OpenAI model, temperature, and API key
-   Swagger UI / OpenAPI documentation
-   GET and POST endpoints for testing AI chat

## Technology stack

-   Java 17 or later
-   Spring Boot 3.x
-   Maven
-   LangChain4j `1.23.0`
-   OpenAI API
-   Springdoc OpenAPI

> This README assumes the project uses the direct `langchain4j-open-ai`
> dependency and manually configures the `ChatModel` bean. It does not
> use the LangChain4j Spring Boot starter.

## Project structure

The package names below are examples. Adjust them to match your project.

``` text
src/
└── main/
    ├── java/com/example/demo/
    │   ├── AiApplication.java
    │   ├── config/
    │   │   ├── AiConfig.java
    │   │   └── OpenApiConfig.java
    │   ├── controller/
    │   │   └── AiController.java
    │   └── service/
    │       └── AiService.java
    └── resources/
        └── application.yml
pom.xml
README.md
```

## Prerequisites

1.  Install JDK 17 or later.
2.  Install Maven, or use the Maven wrapper if the project includes one.
3.  Have an OpenAI API key with API access and billing configured.
4.  Use Spring Boot 3.x with a compatible Springdoc OpenAPI 2.x release.

## Maven dependencies

Add the following dependencies to `pom.xml`. Keep the Spring Boot
parent/plugin and other project configuration already present in your
project.

``` xml
<dependencies>
    <!-- Spring Boot REST APIs -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- LangChain4j OpenAI integration -->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-open-ai</artifactId>
        <version>1.23.0</version>
    </dependency>

    <!-- Swagger UI / OpenAPI 3 -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.13</version>
    </dependency>
</dependencies>
```

If you use a different Spring Boot version, check Springdoc's
compatibility documentation and select a matching Springdoc version.

## Configuration

### 1. Set the OpenAI API key

The application reads the key from the `OPENAI_API_KEY` environment
variable. Do not hard-code the key in source code or commit it to Git.

**Windows PowerShell (current terminal session):**

``` powershell
$env:OPENAI_API_KEY="your-openai-api-key"
```

**macOS / Linux (current terminal session):**

``` bash
export OPENAI_API_KEY="your-openai-api-key"
```

**Spring Tool Suite (STS / Eclipse):**

1.  Right-click the project and choose **Run As → Run
    Configurations...**
2.  Select the application's **Spring Boot App** run configuration.
3.  Open the **Environment** tab.
4.  Click **New...**.
5.  Set the name to `OPENAI_API_KEY` and the value to your API key.
6.  Click **OK**, **Apply**, and **Run**.

### 2. `application.yml`

``` yaml
spring:
  application:
    name: SpringBootLangChain4JIntegration

server:
  port: 8080

app:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      model-name: gpt-4o-mini
      temperature: 0.7

springdoc:
  swagger-ui:
    path: /swagger-ui.html
    operations-sorter: alpha
    tags-sorter: alpha
  api-docs:
    path: /v3/api-docs
```

The `${OPENAI_API_KEY}` placeholder makes Spring resolve the key from
the environment. The configured model must be available to your OpenAI
API account.

## Run the application

From the project root:

``` bash
mvn clean install
mvn spring-boot:run
```

Alternatively, run the main class from STS/Eclipse using **Run As →
Spring Boot App**. Ensure `OPENAI_API_KEY` is set in that run
configuration.

The application listens on:

``` text
http://localhost:8080
```

## REST API endpoints

  ----------------------------------------------------------------------------
  Method                  Endpoint                     Description
  ----------------------- ---------------------------- -----------------------
  `GET`                   `/api/ai/health`             Check whether the
                                                       application is running

  `GET`                   `/api/ai/chat?message=...`   Send a simple prompt
                                                       using a query parameter

  `POST`                  `/api/ai/chat`               Send a prompt in a JSON
                                                       request body
  ----------------------------------------------------------------------------

### 1. Health check

``` http
GET http://localhost:8080/api/ai/health
```

Example response:

``` text
Spring Boot LangChain4j Application Running!
```

### 2. GET chat

``` http
GET http://localhost:8080/api/ai/chat?message=What%20is%20Spring%20Boot%3F
```

Example response:

``` json
{
  "question": "What is Spring Boot?",
  "response": "Spring Boot is ..."
}
```

GET is convenient for short test prompts. Avoid putting sensitive or
lengthy prompts in URLs because URLs may be retained in browser history,
proxy logs, and server logs.

### 3. POST chat

``` http
POST http://localhost:8080/api/ai/chat
Content-Type: application/json
```

Request body:

``` json
{
  "message": "Explain Spring Boot microservices with an example"
}
```

Example response:

``` json
{
  "question": "Explain Spring Boot microservices with an example",
  "response": "Spring Boot microservices ..."
}
```

The exact AI response will vary.

## Swagger UI

After starting the application, open:

-   Swagger UI: <http://localhost:8080/swagger-ui.html>
-   OpenAPI JSON: <http://localhost:8080/v3/api-docs>

In Swagger UI, expand an endpoint, select **Try it out**, enter the
request data, and click **Execute**.

## Testing with Postman

For the POST endpoint:

1.  Set the method to `POST`.
2.  Use `http://localhost:8080/api/ai/chat`.
3.  Select **Body → raw → JSON**.
4.  Enter:

``` json
{
  "message": "What is LangChain4j?"
}
```

5.  Click **Send**.

## How the request flows

``` text
Postman / Swagger UI
        |
        v
Spring Boot REST Controller
        |
        v
AiService
        |
        v
LangChain4j ChatModel
        |
        v
OpenAI API
        |
        v
AI response returned to the REST client
```

## Security notes

-   Never commit API keys, passwords, or other secrets to Git.
-   Keep `OPENAI_API_KEY` in your local environment or a managed secrets
    store.
-   If an API key was accidentally committed, revoke/rotate it and
    remove it from the repository history as appropriate.
-   Restrict CORS and secure the endpoints before exposing the
    application beyond local development.
-   Consider authentication, rate limiting, request validation, and
    usage/cost controls before production deployment.

## Troubleshooting

### `OPENAI_API_KEY` is not set

Confirm that the variable is configured in the same environment or STS
run configuration used to launch the application. Restart the
application after changing it.

### Maven cannot resolve a dependency

-   Check the artifact coordinates and version in `pom.xml`.
-   In STS/Eclipse, right-click the project and select **Maven → Update
    Project**.
-   In a terminal, run:

``` bash
mvn -U clean install
```

-   Check your network, Maven settings, and Maven Central availability.

### OpenAI request fails

Check that the API key is valid, the selected model is available to your
account, and API access/billing are configured. Review the application
logs for the provider's error response. Do not log the API key.

## License

Add the license that applies to your project before publishing it
publicly.
