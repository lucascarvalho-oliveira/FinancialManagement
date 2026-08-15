<p align="center">
  <a href="../README.md">
    <img src="https://img.shields.io/badge/Language-Português-green?style=for-the-badge">
  </a>
</p>


# FinancialManagement

REST API for personal financial management developed with **Java, Spring Boot, Spring Security, JWT, JPA/Hibernate and MySQL**.

The project allows users to register, authenticate with JWT, verify email, register income and expenses, update and delete accounts, and view a financial summary.

## Technologies used

* Java 25
* Spring Boot 4.1.0
* Spring Web MVC
* Spring Data JPA
* Hibernate
* Spring Security
* JWT with JJWT
* BCrypt
* Bean Validation
* Spring Mail
* MySQL
* Maven
* Docker
* Render
* Aiven MySQL

## Features

* User registration
* Password encryption with BCrypt
* Login with email and password
* Authentication using JWT
* Endpoint protection with Spring Security
* Email verification by code
* Email update
* Income and expense registration
* Account listing
* Account value update
* Account deletion
* Total income calculation
* Total expense calculation
* Balance calculation
* Global exception handling
* Data persistence with MySQL
* Deployment using Docker

## Architecture

The project uses a layered architecture:

```text
controller
    ↓
service
    ↓
repository
    ↓
database
```

Main structure:

```text
src/main/java/io/lucascarvalho_oliveira/FinancialManagement

├── config
├── controller
├── dto
├── exception
├── model
├── repository
└── service
```

## Authentication

The system uses authentication based on **JWT (JSON Web Token)**.

Flow:

```text
User
   ↓
POST /pessoa/login
   ↓
Email + password
   ↓
Password validation with BCrypt
   ↓
Email verification
   ↓
JWT generation
   ↓
Token returned to the client
```

To access protected endpoints:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

## Email verification

Flow:

```text
User requests code
        ↓
Code is generated
        ↓
Code + expiration are stored
        ↓
Email is sent
        ↓
User enters the code
        ↓
System validates the code
        ↓
Email is marked as verified
```

## Main endpoints

### Register user

```http
POST /pessoa/salvar
```

### Login

```http
POST /pessoa/login
```

### Update email

```http
PUT /pessoa/atualizar
```

### Financial summary

```http
GET /pessoa/resumo/{id}
```

### Send verification code

```http
POST /email/emailPessoa
```

### Validate code

```http
POST /email/validacao
```

### Register account

```http
POST /conta/salvar
```

### List accounts

```http
GET /conta/listar
```

### Update value

```http
PUT /conta/atualizar/{id}/valor
```

### Delete account

```http
DELETE /conta/delete/{id}
```

## Database

The project uses MySQL hosted on **Aiven**.

Main tables:

```text
pessoa
conta
codigo_verificacao
```

Main relationship:

```text
Pessoa 1 ───── N Conta
```

## Deployment on Render

The application is hosted on **Render** using Docker.

Architecture:

```text
Client / Postman
        ↓
Render
        ↓
Docker Container
        ↓
Spring Boot
        ↓
Aiven MySQL
```

Online API:

```text
https://financialmanagement-7isv.onrender.com
```

Render uses the `Dockerfile` present in the repository to build and run the application.

Environment variables are configured directly in the Render dashboard.

> Since the project uses a free Render instance, the first request after a period of inactivity may take a few seconds while the service starts again.

## Error handling

The project uses `@RestControllerAdvice` for global exception handling.

Handled errors include:

* Person not found
* Person already exists
* Invalid password
* Account not found
* Incorrect verification code

## Project objective

The project was developed to consolidate back-end development knowledge, including:

* REST APIs
* layered architecture
* JPA/Hibernate
* Spring Security
* JWT
* BCrypt
* email sending and validation
* exception handling
* Docker
* cloud database
* deployment of Spring Boot applications

## Author

**Lucas Carvalho**

GitHub: `lucascarvalho-oliveira`
