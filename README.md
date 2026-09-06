# HeFishing

Sistema para gerenciamento de produtos de uma loja de artigos de pesca. O projeto foi desenvolvido como atividade acadêmica durante o curso de Sistemas de Informação, utilizando Java e Spring Boot.

Durante o desenvolvimento foram aplicados os conteúdos das aulas do curso SuporteOS 2026, passando desde a configuração do ambiente até a criação de uma API REST com banco de dados.

---

## Sobre o projeto

O HeFishing permite cadastrar e consultar produtos, grupos de produtos e fornecedores.

Nos produtos são controladas informações como:

* código de barras;
* descrição;
* quantidade em estoque;
* valor unitário;
* estoque mínimo;
* grupo do produto;
* fornecedor;
* status;
* valor total em estoque.

O projeto foi desenvolvido seguindo as etapas das Aulas 00 a 07.

## Andamento das aulas

| Aula | Conteúdo                              | Status    |
| ---- | ------------------------------------- | --------- |
| 00   | Git e GitHub                          | Concluída |
| 01   | Java e Maven                          | Concluída |
| 02   | Projeto Spring Boot                   | Concluída |
| 03   | Modelagem do domínio                  | Concluída |
| 04   | JPA, PostgreSQL e Liquibase           | Concluída |
| 05   | Repositories, Services e Transactions | Concluída |
| 06   | Evolução do modelo e Liquibase Diff   | Concluída |
| 07   | API REST, DTOs, Mappers e erros       | Concluída |

A Aula 07 é a última aula do projeto utilizado no curso.

---

## Tecnologias

* Java 21
* Spring Boot 4.0.7
* Spring Data JPA
* Hibernate
* PostgreSQL 16
* Liquibase
* Maven Wrapper
* JUnit
* Mockito
* MockMvc
* Postman
* Git
* GitHub

---

## Estrutura

O projeto está organizado da seguinte forma:

```text
ProjetoHeytor/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/curso/hefishing/
│   │   │   ├── api/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── exception/
│   │   │   │   └── mapper/
│   │   │   ├── controller/
│   │   │   ├── domain/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   │
│   │   └── resources/
│   │       ├── db/changelog/
│   │       └── application*.properties
│   │
│   └── test/
│       ├── java/
│       └── resources/
│
├── .editorconfig
├── .env.example
├── .gitattributes
├── .gitignore
├── HELP.md
├── README.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## Organização do código

### Domain

Contém as classes principais do sistema:

* `Produto`
* `GrupoProduto`
* `Fornecedor`
* `Status`

### Repository

Responsável pelo acesso ao banco através do Spring Data JPA:

* `ProdutoRepository`
* `GrupoProdutoRepository`
* `FornecedorRepository`

### Service

Contém as regras de negócio e as operações com o banco:

* `ProdutoService`
* `GrupoProdutoService`
* `FornecedorService`

Também são utilizadas exceções para situações como recurso não encontrado e cadastro duplicado.

### API

É a parte responsável pelas requisições HTTP.

Nela estão os:

* Controllers;
* DTOs;
* Mappers;
* tratamento de erros.

---

## Banco de dados

O banco utilizado no projeto é o PostgreSQL.

Durante o desenvolvimento foram utilizados os bancos:

```text
hefishing_dev
hefishing_test
```

A criação e alteração das tabelas é feita pelo Liquibase.

Os arquivos ficam em:

```text
src/main/resources/db/changelog/
```

Changelogs principais:

```text
001-create-grupo-produto.yaml
002-create-produto.yaml
003-fornecedor-e-estoque-minimo.yaml
```

O Hibernate está configurado para validar a estrutura do banco, enquanto o Liquibase é responsável pelas alterações do schema.

---

## Credenciais

As senhas do PostgreSQL não ficam dentro do código versionado.

O projeto utiliza variáveis de ambiente, por exemplo:

```properties
spring.datasource.password=${DB_DEV_PASSWORD}
```

O arquivo `.env.example` serve apenas como referência para as variáveis utilizadas.

Senhas reais e outras informações sensíveis não devem ser colocadas no Git.

---

## Como executar

### Pré-requisitos

É necessário ter instalado:

* Java 21;
* PostgreSQL;
* IntelliJ IDEA ou outra IDE compatível.

Não é necessário instalar o Maven separadamente, pois o projeto possui Maven Wrapper.

### Clonar o projeto

```bash
git clone https://github.com/heytoreduardo/ProjetoHeytor.git
```

Depois:

```bash
cd ProjetoHeytor
```

### Banco de dados

Crie os bancos:

```text
hefishing_dev
hefishing_test
```

Depois configure as variáveis de ambiente utilizadas pelo projeto para acessar o PostgreSQL.

As tabelas não precisam ser criadas manualmente. O Liquibase faz essa parte quando a aplicação é executada.

### Executar os testes

No Windows:

```powershell
.\mvnw.cmd test
```

No Linux/macOS:

```bash
./mvnw test
```

Atualmente o projeto possui 41 testes e todos estão passando.

Resultado esperado:

```text
Tests run: 41, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

### Executar a aplicação

No Windows:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

A aplicação utiliza a porta:

```text
8080
```

---

## Health Check

Para verificar se a aplicação está funcionando:

```http
GET http://localhost:8080/api/health
```

Resposta:

```text
OK
```

---

# API

## Grupos de produtos

### Cadastrar grupo

```http
POST /api/grupos-produtos
```

Exemplo:

```json
{
  "nome": "Iscas Artificiais"
}
```

### Listar grupos

```http
GET /api/grupos-produtos
```

---

## Fornecedores

### Cadastrar fornecedor

```http
POST /api/fornecedores
```

Exemplo:

```json
{
  "razaoSocial": "Pesca Forte Equipamentos Ltda",
  "cnpj": "98765432000110"
}
```

### Listar fornecedores

```http
GET /api/fornecedores
```

---

## Produtos

### Cadastrar produto

```http
POST /api/produtos
```

Exemplo:

```json
{
  "codigoBarras": "7890000000999",
  "descricao": "Isca Artificial Teste",
  "saldoEstoque": 25,
  "valorUnitario": 39.90,
  "estoqueMinimo": 5,
  "grupoId": 1,
  "fornecedorId": 2
}
```

### Buscar produto

```http
GET /api/produtos/{id}
```

Exemplo:

```http
GET /api/produtos/2
```

### Listar produtos

```http
GET /api/produtos
```

---

# Tratamento de erros

A API possui respostas específicas para alguns erros.

### 400 Bad Request

Usado quando os dados enviados não passam pelas validações ou quando o JSON está incorreto.

### 404 Not Found

Usado quando o recurso solicitado não existe.

Exemplo:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Produto não encontrado: 999"
}
```

### 409 Conflict

Usado quando existe um conflito com um registro já cadastrado.

Por exemplo, tentar cadastrar novamente um produto com o mesmo código de barras.

---

# DTOs

Os DTOs são utilizados para definir os dados que entram e saem da API.

Principais classes:

```text
ProdutoRequest
ProdutoResponse

GrupoProdutoRequest
GrupoProdutoResponse

FornecedorRequest
FornecedorResponse
```

Também são utilizadas validações como:

* `@NotBlank`
* `@NotNull`
* `@Positive`
* `@PositiveOrZero`
* `@Size`

---

# Mappers

Os mappers fazem a conversão entre as entidades e os DTOs.

Classes utilizadas:

```text
ProdutoMapper
GrupoProdutoMapper
FornecedorMapper
```

A ideia é deixar essa conversão separada das regras de negócio e dos Controllers.

---

# Testes

Foram criados testes para diferentes partes da aplicação.

```text
HeFishingApplicationTests
PersistenciaTest
IntegridadeBancoTest

ProdutoTest
GrupoProdutoTest
FornecedorTest

ProdutoServiceTest
GrupoProdutoServiceTest
FornecedorServiceTest

ProdutoControllerTest
```

Resultado atual:

```text
41 testes executados
0 falhas
0 erros

BUILD SUCCESS
```

Também existe um teste relacionado à integridade do banco, verificando, entre outras coisas, que não é possível inserir estoque negativo.

---

# Postman

Foi criada uma Collection do Postman para testar os endpoints da API.

Os testes realizados foram:

```text
00 - Health check
01 - Cadastrar grupo
02 - Cadastrar fornecedor
03 - Cadastrar produto
04 - Buscar produto por ID
05 - Listar grupos
06 - Listar produtos
07 - Produto inválido - 400
08 - Produto inexistente - 404
09 - Código de barras duplicado - 409
10 - JSON malformado - 400
```

A Collection também está disponível no repositório:

```text
HeFishing - Aula 07.postman_collection.json
```

Ela pode ser importada no Postman para testar a API em outro computador.

---

# Git e GitHub

Repositório:

```text
https://github.com/heytoreduardo/ProjetoHeytor
```

Branch principal:

```text
main
```

Foram criadas tags para marcar o final de cada etapa:

```text
aula-00-inicio
aula-01-ambiente
aula-02-criacao-projeto
aula-03-modelagem-dominio
aula-04-persistencia
aula-05-repositories-servicos-transacoes
aula-06-evolucao-modelo-liquibase-diff
aula-07-api-rest-dtos-mappers
```

---

# Conclusão

O HeFishing foi desenvolvido durante as Aulas 00 a 07, acompanhando a evolução do projeto desde a configuração do ambiente até a criação da API REST.

Durante o desenvolvimento foram utilizados conceitos de:

* Java;
* Maven;
* Spring Boot;
* modelagem de domínio;
* JPA e Hibernate;
* PostgreSQL;
* Liquibase;
* Spring Data JPA;
* Services;
* Transactions;
* DTOs;
* Mappers;
* validação;
* API REST;
* tratamento de exceções;
* testes automatizados;
* MockMvc;
* Postman;
* Git e GitHub.

O projeto está concluído até a Aula 07.

## Projeto acadêmico

**HeFishing**

Sistema de gerenciamento de produtos para uma loja de artigos de pesca.

Projeto desenvolvido para fins acadêmicos no curso de Sistemas de Informação.
