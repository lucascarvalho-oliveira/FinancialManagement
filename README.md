<p align="center">
  <a href="./docs/README.en.md">
    <img src="https://img.shields.io/badge/Language-English-blue?style=for-the-badge">
  </a>
</p>


# FinancialManagement

API REST para gerenciamento financeiro pessoal desenvolvida com **Java, Spring Boot, Spring Security, JWT, JPA/Hibernate e MySQL**.

O projeto permite cadastrar usuários, autenticar com JWT, verificar e-mail, cadastrar receitas e despesas, atualizar e excluir contas e consultar um resumo financeiro.

## Tecnologias utilizadas

* Java 25
* Spring Boot 4.1.0
* Spring Web MVC
* Spring Data JPA
* Hibernate
* Spring Security
* JWT com JJWT
* BCrypt
* Bean Validation
* Spring Mail
* MySQL
* Maven
* Docker
* Render
* Aiven MySQL

## Funcionalidades

* Cadastro de usuários
* Criptografia de senha com BCrypt
* Login com e-mail e senha
* Autenticação utilizando JWT
* Proteção de endpoints com Spring Security
* Verificação de e-mail por código
* Alteração de e-mail
* Cadastro de receitas e despesas
* Listagem de contas
* Atualização do valor de uma conta
* Exclusão de contas
* Cálculo de total de receitas
* Cálculo de total de despesas
* Cálculo de saldo
* Tratamento global de exceções
* Persistência de dados com MySQL
* Deploy utilizando Docker

## Arquitetura

O projeto utiliza uma organização em camadas:

```text
controller
    ↓
service
    ↓
repository
    ↓
database
```

Estrutura principal:

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

## Autenticação

O sistema utiliza autenticação baseada em **JWT (JSON Web Token)**.

Fluxo:

```text
Usuário
   ↓
POST /pessoa/login
   ↓
E-mail + senha
   ↓
Validação da senha com BCrypt
   ↓
Verificação do e-mail
   ↓
Geração do JWT
   ↓
Token retornado ao cliente
```

Para acessar endpoints protegidos:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

## Verificação de e-mail

Fluxo:

```text
Usuário solicita código
        ↓
Código é gerado
        ↓
Código + expiração são armazenados
        ↓
E-mail é enviado
        ↓
Usuário informa o código
        ↓
Sistema valida o código
        ↓
E-mail é marcado como verificado
```

## Principais endpoints

### Cadastrar usuário

```http
POST /pessoa/salvar
```

### Login

```http
POST /pessoa/login
```

### Atualizar e-mail

```http
PUT /pessoa/atualizar
```

### Resumo financeiro

```http
GET /pessoa/resumo/{id}
```

### Enviar código de verificação

```http
POST /email/emailPessoa
```

### Validar código

```http
POST /email/validacao
```

### Cadastrar conta

```http
POST /conta/salvar
```

### Listar contas

```http
GET /conta/listar
```

### Atualizar valor

```http
PUT /conta/atualizar/{id}/valor
```

### Excluir conta

```http
DELETE /conta/delete/{id}
```

## Banco de dados

O projeto utiliza MySQL hospedado na **Aiven**.

Principais tabelas:

```text
pessoa
conta
codigo_verificacao
```

Relacionamento principal:

```text
Pessoa 1 ───── N Conta
```

## Deploy no Render

A aplicação está hospedada no **Render** utilizando Docker.

Arquitetura:

```text
Cliente / Postman
        ↓
Render
        ↓
Container Docker
        ↓
Spring Boot
        ↓
Aiven MySQL
```

API online:

```text
https://financialmanagement-7isv.onrender.com
```

O Render utiliza o `Dockerfile` presente no repositório para construir e executar a aplicação.

As variáveis de ambiente são configuradas diretamente no painel do Render.

> Como o projeto utiliza uma instância gratuita do Render, a primeira requisição após um período de inatividade pode levar alguns segundos enquanto o serviço é iniciado novamente.

## Tratamento de erros

O projeto utiliza `@RestControllerAdvice` para tratamento global de exceções.

Entre os erros tratados estão:

* Pessoa não encontrada
* Pessoa já existente
* Senha inválida
* Conta não encontrada
* Código de verificação incorreto

## Objetivo do projeto

O projeto foi desenvolvido para consolidar conhecimentos de desenvolvimento back-end, incluindo:

* APIs REST
* arquitetura em camadas
* JPA/Hibernate
* Spring Security
* JWT
* BCrypt
* envio e validação de e-mails
* tratamento de exceções
* Docker
* banco de dados em nuvem
* deploy de aplicações Spring Boot

## Autor

**Lucas Carvalho**

GitHub: `lucascarvalho-oliveira`
