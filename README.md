# 🎣 HeFishing

Sistema de gerenciamento de produtos para uma loja de artigos de pesca, desenvolvido como projeto acadêmico durante o curso de desenvolvimento com Java e Spring Boot.

O projeto foi construído acompanhando as aulas do curso **SuporteOS 2026**, aplicando conceitos de Java, Spring Boot, modelagem de domínio, JPA, PostgreSQL, Liquibase, Spring Data JPA, serviços transacionais e desenvolvimento de APIs REST.

---

## 📚 Status do Projeto

**Projeto concluído até a Aula 07.**

| Aula    | Conteúdo                                      | Status      |
| ------- | --------------------------------------------- | ----------- |
| Aula 00 | Git e GitHub                                  | ✅ Concluída |
| Aula 01 | Ambiente Java e Maven                         | ✅ Concluída |
| Aula 02 | Criação do projeto Spring Boot                | ✅ Concluída |
| Aula 03 | Modelagem do domínio                          | ✅ Concluída |
| Aula 04 | Persistência com JPA, PostgreSQL e Liquibase  | ✅ Concluída |
| Aula 05 | Repositories, Services e Transactions         | ✅ Concluída |
| Aula 06 | Evolução do modelo e Liquibase Diff           | ✅ Concluída |
| Aula 07 | API REST, DTOs, Mappers e tratamento de erros | ✅ Concluída |

> **Aula 07 é a última aula do projeto. Não existe Aula 08 no roteiro utilizado.**

---

## 🎯 Objetivo

O HeFishing tem como objetivo fornecer uma API REST para gerenciamento de produtos de uma loja de artigos de pesca.

O sistema permite trabalhar com:

* Produtos;
* Grupos de produtos;
* Fornecedores;
* Controle de estoque;
* Estoque mínimo;
* Valor unitário;
* Valor total em estoque;
* Código de barras;
* Status dos registros;
* Relacionamentos entre produtos, grupos e fornecedores.

---

## 🛠️ Tecnologias utilizadas

* **Java 21**
* **Spring Boot 4.0.7**
* **Spring Data JPA**
* **Hibernate**
* **PostgreSQL 16**
* **Liquibase**
* **Maven Wrapper**
* **JUnit**
* **Mockito**
* **MockMvc**
* **Postman**
* **Git**
* **GitHub**

---

## 🏗️ Estrutura do projeto

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

---

## 🧩 Organização das camadas

O projeto utiliza uma separação por responsabilidades.

### Domain

Contém as entidades e regras principais do sistema:

* `Produto`
* `GrupoProduto`
* `Fornecedor`
* `Status`

### Repository

Responsável pelo acesso aos dados utilizando Spring Data JPA:

* `ProdutoRepository`
* `GrupoProdutoRepository`
* `FornecedorRepository`

### Service

Concentra as regras de negócio e operações transacionais:

* `ProdutoService`
* `GrupoProdutoService`
* `FornecedorService`

Também possui exceções específicas para:

* recurso não encontrado;
* recurso duplicado.

### API

Responsável pela comunicação HTTP:

* Controllers;
* DTOs;
* Mappers;
* Tratamento padronizado de erros.

---

## 🗄️ Banco de dados

O projeto utiliza **PostgreSQL** como banco de dados.

São utilizados os seguintes bancos durante o desenvolvimento e os testes:

```text
hefishing_dev
hefishing_test
```

A estrutura do banco é controlada pelo **Liquibase**.

Os changelogs ficam em:

```text
src/main/resources/db/changelog/
```

Principais alterações de banco:

```text
001-create-grupo-produto.yaml
002-create-produto.yaml
003-fornecedor-e-estoque-minimo.yaml
```

O Liquibase é utilizado como fonte de verdade para a evolução do banco.

O Hibernate está configurado para validar o schema, evitando que a aplicação altere automaticamente a estrutura do banco.

---

## 🔐 Configuração de credenciais

As credenciais do PostgreSQL **não são armazenadas no Git**.

O projeto utiliza variáveis de ambiente para as senhas do banco.

Exemplo:

```properties
spring.datasource.password=${DB_DEV_PASSWORD}
```

As configurações sensíveis devem ser definidas no ambiente de execução.

O arquivo:

```text
.env.example
```

serve como referência para as variáveis necessárias.

> **Nunca coloque senhas reais, tokens ou outras credenciais diretamente nos arquivos versionados do projeto.**

---

## ▶️ Como executar o projeto

### Pré-requisitos

Instale no computador:

* Java 21;
* PostgreSQL;
* IntelliJ IDEA ou outra IDE compatível.

O Maven não precisa ser instalado separadamente, pois o projeto utiliza o **Maven Wrapper**.

---

### 1. Clonar o projeto

```bash
git clone https://github.com/heytoreduardo/ProjetoHeytor.git
```

Entrar na pasta:

```bash
cd ProjetoHeytor
```

---

### 2. Configurar o PostgreSQL

Crie os bancos:

```text
hefishing_dev
hefishing_test
```

Configure as variáveis de ambiente necessárias para acesso ao PostgreSQL.

---

### 3. Executar os testes

No Windows:

```powershell
.\mvnw.cmd test
```

No Linux/macOS:

```bash
./mvnw test
```

O projeto atualmente possui **41 testes automatizados**, todos passando.

Resultado esperado:

```text
Tests run: 41, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

---

### 4. Executar a aplicação

No Windows:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

A aplicação será executada na porta:

```text
8080
```

---

## ❤️ Health Check

Para verificar se a aplicação está funcionando:

```http
GET http://localhost:8080/api/health
```

Resposta esperada:

```text
OK
```

---

# 🌐 API REST

A API utiliza endpoints para gerenciamento dos principais recursos do sistema.

## Grupos de produtos

### Criar grupo

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

### Criar fornecedor

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

### Criar produto

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

### Buscar produto por ID

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

# ⚠️ Tratamento de erros

A API possui tratamento padronizado para erros HTTP.

### Dados inválidos

```text
400 Bad Request
```

Utilizado para erros de validação dos dados enviados.

### JSON malformado

```text
400 Bad Request
```

Utilizado quando o corpo da requisição não possui JSON válido.

### Recurso não encontrado

```text
404 Not Found
```

Exemplo:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Produto não encontrado: 999"
}
```

### Recurso duplicado

```text
409 Conflict
```

Exemplo: tentativa de cadastrar um produto com código de barras já existente.

---

# 📦 DTOs

A API utiliza DTOs para separar o modelo interno das informações expostas pela API.

Principais DTOs:

```text
ProdutoRequest
ProdutoResponse

GrupoProdutoRequest
GrupoProdutoResponse

FornecedorRequest
FornecedorResponse
```

Os DTOs também utilizam validações como:

* `@NotBlank`
* `@NotNull`
* `@Positive`
* `@PositiveOrZero`
* `@Size`

---

# 🔄 Mappers

Os mappers são responsáveis pela conversão entre entidades e DTOs.

Principais mappers:

```text
ProdutoMapper
GrupoProdutoMapper
FornecedorMapper
```

Eles não realizam consultas ao banco, não controlam transações e não possuem responsabilidade sobre HTTP.

---

# 🧪 Testes

O projeto possui testes de diferentes camadas:

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

### Resultado atual

```text
41 testes executados
0 falhas
0 erros
```

```text
BUILD SUCCESS
```

Também existe teste específico para verificar a integridade do banco, incluindo a restrição que impede estoque negativo.

---

# 📬 Postman

Os testes da API podem ser realizados utilizando o Postman.

A Collection utilizada no projeto contém testes para:

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

A Collection pode ser exportada e importada no Postman para utilização em outro computador.

---

# 🌿 Git e GitHub

Repositório:

```text
https://github.com/heytoreduardo/ProjetoHeytor
```

Branch principal:

```text
main
```

O projeto possui checkpoints por aula através de tags Git.

### Tags

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

# 🎓 Conclusão

O HeFishing foi desenvolvido como projeto acadêmico para aplicar, de forma progressiva, os conceitos apresentados durante as Aulas 00 a 07.

Ao final do projeto, foram aplicados conceitos de:

* Git e GitHub;
* Java 21;
* Maven;
* Spring Boot;
* Modelagem de domínio;
* JPA e Hibernate;
* PostgreSQL;
* Liquibase;
* Spring Data JPA;
* Services;
* Transactions;
* DTOs;
* Mappers;
* Validação;
* API REST;
* Tratamento de exceções;
* Testes automatizados;
* MockMvc;
* Postman.

**Status final: Projeto concluído até a Aula 07. ✅**

---

## 👨‍💻 Projeto acadêmico

**HeFishing — Sistema de gerenciamento de produtos para artigos de pesca**

Desenvolvido para fins acadêmicos durante o curso de Sistemas de Informação.
