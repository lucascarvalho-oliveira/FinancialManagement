<p align="center">
  <a href="../README.md">
    <img src="https://img.shields.io/badge/Idioma-Português-green?style=for-the-badge">
  </a>
</p>

# FinancialManagement

REST API for personal finance tracking: users record income and expenses and check their monthly balance. It uses JWT authentication and email verification by code.

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-green)
![MySQL](https://img.shields.io/badge/MySQL-Aiven-blue)
![Deploy](https://img.shields.io/badge/Deploy-Render-purple)

**Live API:** https://financialmanagement-7isv.onrender.com
**Interactive docs (Swagger):** https://financialmanagement-7isv.onrender.com/swagger-ui.html

> The app runs on Render's free plan. After a period of inactivity, the first request may take a few seconds while the service starts up again.

## Features

- Register users with encrypted passwords (BCrypt)
- Verify the email with a 6-digit code, valid for 15 minutes
- Log in and receive a JWT, valid for 1 hour
- Block login for users who have not verified their email
- Create, list, update the amount of, and delete income and expense entries
- Calculate total income, total expenses, and balance
- Change the account email, which requires a new verification

## Tech stack

- Java 25, Spring Boot 4.1
- Spring Web MVC, Spring Data JPA, Bean Validation
- Spring Security + JWT (JJWT 0.12) + BCrypt
- MySQL hosted on Aiven
- Email delivery through the Resend API
- springdoc-openapi (Swagger UI)
- JUnit 5 + Mockito
- Docker + Render

## Architecture

```text
Controller  ->  Service  ->  Repository  ->  MySQL
```

```text
src/main/java/io/lucascarvalho_oliveira/FinancialManagement
├── config       # Spring Security, JWT filter, BCrypt
├── controller   # REST endpoints
├── dto          # request and response objects
├── exception    # business exceptions and @RestControllerAdvice
├── model        # JPA entities (Pessoa, Conta, CodigoVerificacao)
├── repository   # Spring Data JPA
└── service      # business rules, JWT, and email delivery
```

Main relationship: `Pessoa 1 ── N Conta` (person and entries). Each entry is either `RECEITA` (income) or `DESPESA` (expense).

### Authentication flow

```text
Sign up -> request code -> email with code -> validate code -> email verified
                                                                     |
                                                                   Login
                                                                     |
                                                     password checked with BCrypt
                                                                     |
                                                                    JWT
```

For protected endpoints, send the token in the header:

```http
Authorization: Bearer YOUR_JWT
```

## Running locally

**Requirements:** JDK 25, MySQL, and a [Resend](https://resend.com) API key.

1. Clone the repository:

```bash
git clone https://github.com/lucascarvalho-oliveira/FinancialManagement.git
cd FinancialManagement
```

2. Create the database tables. Hibernate runs with `ddl-auto: validate`, so it checks the tables but does not create them.

3. Set the environment variables:

| Variable | Example |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/financial` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `your_password` |
| `JWT_SECRET` | a key with at least 32 characters |
| `RESEND_API_KEY` | `re_xxxxxxxx` |
| `PORT` | `8080` (optional) |

4. Start the application:

```bash
./mvnw spring-boot:run
```

The API runs at http://localhost:8080 and Swagger at http://localhost:8080/swagger-ui.html.

With Docker:

```bash
docker build -t financial-management .
docker run -p 8080:8080 --env-file .env financial-management
```

## Main endpoints

The full list, with request and response examples, is in [Swagger](https://financialmanagement-7isv.onrender.com/swagger-ui.html).

| Method | Route | Description | Auth |
|---|---|---|---|
| POST | `/pessoa/salvar` | Register a user | no |
| POST | `/email/emailPessoa` | Send the verification code | no |
| POST | `/email/validacao` | Validate the code | no |
| POST | `/pessoa/login` | Generate the JWT | no |
| PUT | `/pessoa/atualizar` | Change the email | no |
| GET | `/pessoa/resumo/{id}` | Totals and balance | yes |
| POST | `/conta/salvar` | Create an income or expense | yes |
| GET | `/conta/listar` | List entries | yes |
| PUT | `/conta/atualizar/{id}/valor` | Update the amount | yes |
| DELETE | `/conta/delete/{id}` | Delete an entry | yes |

## Tests

```bash
./mvnw test
```

Current tests cover `CodigoVerificacaoService` with Mockito: valid code, wrong code, and expired code.

## Technical decisions

- **Resend instead of SMTP:** Render's free plan blocks outgoing SMTP. Resend sends email through an HTTP API, which works in that environment.
- **`BigDecimal` for amounts:** `double` causes rounding errors with money.
- **Email verification by code:** [why this flow]
- **MySQL on Aiven:** [why Aiven]

## Known limitations and next steps

- **No ownership checks yet.** Any logged-in user can list every user's entries, see another person's summary by `id`, and update or delete entries that are not theirs. Next step: read the user from the JWT and filter by it.
- **Sign-up returns the full entity**, including the password hash. Next step: respond with a DTO.
- **Few tests:** only the verification service is covered. `PessoaService`, `ContaService`, and controller tests are missing.
- **Migration to PostgreSQL** is planned but not done.

## Author

**Lucas Carvalho**

- LinkedIn: [linkedin.com/in/lucas-carvalho-jesus](https://www.linkedin.com/in/lucas-carvalho-jesus)
- Email: lucascarvalhoptc09@gmail.com
- GitHub: [lucascarvalho-oliveira](https://github.com/lucascarvalho-oliveira)
