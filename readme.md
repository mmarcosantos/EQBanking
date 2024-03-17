
# EQ Bank

  

## Introduction

For this project I created an API using Java with Springboot simulating a simple banking system.

## Endpoints

The API has the following endpoints:
For client:
GET /customers

POST /customers

GET /customers/{id}

PATCH /customers/{id}

For account:
GET /accounts
POST /accounts
POST /accounts/{id}/withdraw
POST /accounts/{id}/deposit
GET /accounts/{id}
GET /accounts/{id}/transactions

## Used technologies: 
Springboot 3.2.3
Java 17
H2 as the database for this project, so you don't need to install any database to run it.
Spring Data JPA to access the database.
Swagger to document the API.
JUnit 5 and Mockito to create the tests.
Docker to run the application in a container
  
## Requirements

- Java 17
- Maven
- Docker (optional)

## How to run

 - Clone the repository
 - Run the command `mvn spring-boot:run` in the root folder of the project
 - The API will be available at http://localhost:8080
**This project is also available in docker:**
 - Run the following commands in the root folder of the project:
	 - docker build -t eqbank . 	
	 - docker run -p 8080:8080 eqbank

  

## How to use

I used Swagger to document the API, so you can access the documentation at http://localhost:8080/swagger-ui.html

You can also use Postman to test the API, the collection is available at the root folder of the project.
  
## How to run tests

- Run the command `mvn test` in the root folder of the project

