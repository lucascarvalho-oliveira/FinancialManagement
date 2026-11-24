<p align="center">
  <a href="./docs/README.en.md">
    <img src="https://img.shields.io/badge/Language-English-blue?style=for-the-badge">
  </a>
</p>

# FinancialManagement

API REST para controle financeiro pessoal: o usuário cadastra receitas e despesas e consulta o saldo do mês. Tem autenticação com JWT e verificação de e-mail por código.

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-green)
![MySQL](https://img.shields.io/badge/MySQL-Aiven-blue)
![Deploy](https://img.shields.io/badge/Deploy-Render-purple)

**API online:** https://financialmanagement-7isv.onrender.com
**Documentação interativa (Swagger):** https://financialmanagement-7isv.onrender.com/swagger-ui.html

> O deploy usa o plano gratuito do Render. Depois de um tempo sem uso, a primeira requisição pode levar alguns segundos enquanto o serviço volta a subir.

## Funcionalidades

- Cadastrar usuário com senha criptografada (BCrypt)
- Verificar o e-mail com um código de 6 dígitos, válido por 15 minutos
- Fazer login e receber um token JWT, válido por 1 hora
- Bloquear o login de quem ainda não verificou o e-mail
- Cadastrar, listar, atualizar o valor e excluir receitas e despesas
- Calcular total de receitas, total de despesas e saldo
- Trocar o e-mail da conta, exigindo nova verificação

## Tecnologias

- Java 25, Spring Boot 4.1
- Spring Web MVC, Spring Data JPA, Bean Validation
- Spring Security + JWT (JJWT 0.12) + BCrypt
- MySQL hospedado na Aiven
- Envio de e-mail pela API do Resend
- springdoc-openapi (Swagger UI)
- JUnit 5 + Mockito
- Docker + Render

## Arquitetura

```text
Controller  ->  Service  ->  Repository  ->  MySQL
```

```text
src/main/java/io/lucascarvalho_oliveira/FinancialManagement
├── config       # Spring Security, filtro JWT, BCrypt
├── controller   # endpoints REST
├── dto          # objetos de entrada e saída
├── exception    # exceções de negócio e @RestControllerAdvice
├── model        # entidades JPA (Pessoa, Conta, CodigoVerificacao)
├── repository   # Spring Data JPA
└── service      # regras de negócio, JWT e envio de e-mail
```

Relacionamento principal: `Pessoa 1 ── N Conta`. Cada conta é do tipo `RECEITA` ou `DESPESA`.

### Fluxo de autenticação

```text
Cadastro -> pede código -> e-mail com código -> valida código -> e-mail verificado
                                                                       |
                                                                     Login
                                                                       |
                                                   senha conferida com BCrypt
                                                                       |
                                                                  token JWT
```

Nos endpoints protegidos, envie o token no cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

## Como rodar

**Pré-requisitos:** JDK 25, MySQL e uma chave de API do [Resend](https://resend.com).

1. Clone o repositório:

```bash
git clone https://github.com/lucascarvalho-oliveira/FinancialManagement.git
cd FinancialManagement
```

2. Crie as tabelas no banco. O Hibernate está em `ddl-auto: validate`, então ele confere as tabelas, mas não cria.

3. Defina as variáveis de ambiente:

| Variável | Exemplo |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/financial` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `sua_senha` |
| `JWT_SECRET` | uma chave com pelo menos 32 caracteres |
| `RESEND_API_KEY` | `re_xxxxxxxx` |
| `PORT` | `8080` (opcional) |

4. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

A API sobe em http://localhost:8080 e o Swagger em http://localhost:8080/swagger-ui.html.

Com Docker:

```bash
docker build -t financial-management .
docker run -p 8080:8080 --env-file .env financial-management
```

## Principais endpoints

A lista completa, com exemplos de requisição e resposta, está no [Swagger](https://financialmanagement-7isv.onrender.com/swagger-ui.html).

| Método | Rota | Descrição | Login |
|---|---|---|---|
| POST | `/pessoa/salvar` | Cadastra usuário | não |
| POST | `/email/emailPessoa` | Envia o código de verificação | não |
| POST | `/email/validacao` | Valida o código | não |
| POST | `/pessoa/login` | Gera o token JWT | não |
| PUT | `/pessoa/atualizar` | Troca o e-mail | não |
| GET | `/pessoa/resumo/{id}` | Totais e saldo | sim |
| POST | `/conta/salvar` | Cadastra receita ou despesa | sim |
| GET | `/conta/listar` | Lista as contas | sim |
| PUT | `/conta/atualizar/{id}/valor` | Atualiza o valor | sim |
| DELETE | `/conta/delete/{id}` | Exclui a conta | sim |

## Testes

```bash
./mvnw test
```

Hoje os testes cobrem o `CodigoVerificacaoService`, com Mockito: código válido, código incorreto e código expirado.

## Decisões técnicas

- **Resend em vez de SMTP:** o plano gratuito do Render bloqueia o envio de e-mail por SMTP. O Resend envia por uma API HTTP, que funciona nesse ambiente.
- **`BigDecimal` para valores:** `double` gera erro de arredondamento em dinheiro.
- **Verificação de e-mail por código:** [por que escolheu esse fluxo]
- **Banco MySQL na Aiven:** [por que escolheu a Aiven]

## Limitações e próximos passos

- **Falta checar o dono dos dados.** Qualquer usuário logado consegue listar as contas de todos os usuários, ver o resumo de outra pessoa pelo `id` e alterar ou excluir contas que não são dele. Próximo passo: pegar o usuário pelo token JWT e filtrar por ele.
- **O cadastro devolve a entidade inteira**, incluindo o hash da senha. Próximo passo: responder com um DTO.
- **Poucos testes:** só o serviço de verificação está coberto. Faltam `PessoaService`, `ContaService` e testes dos controllers.
- **Migração para PostgreSQL** planejada, ainda não feita.

## Autor

**Lucas Carvalho**

- LinkedIn: [linkedin.com/in/lucas-carvalho-jesus](https://www.linkedin.com/in/lucas-carvalho-jesus)
- E-mail: lucascarvalhoptc09@gmail.com
- GitHub: [lucascarvalho-oliveira](https://github.com/lucascarvalho-oliveira)
