# 📈 Price Tracker API

API REST desenvolvida em Java com Spring Boot para rastreamento automático de preços de produtos em e-commerces. O sistema realiza web scraping periódico, armazena o histórico de preços e permite acompanhar a evolução de valores ao longo do tempo.

---

## Autor

**Victor Bertolini**

**Github:** https://github.com/VictorBertolini

**Linkedin:** https://www.linkedin.com/in/victor-bertolini-de-sousa-6b8630394/

---

## Funcionalidades

- Cadastro de usuários e produtos para rastreamento
- Web scraping automático com Selenium (suporte a sites com renderização JavaScript)
- Scraping inicial no momento do cadastro do produto
- Job agendado diário para atualização de preços
- Histórico completo de preços com timestamp
- Alerta por targetPrice — define o valor desejado e acompanhe quando o preço chegar lá
- Suporte a lojas conhecidas com XPath gerenciado internamente, ou XPath customizado para qualquer site

---

## 🛠️ Stack

| Tecnologia         | Uso                   |
|--------------------|-----------------------|
| Java 21            | Linguagem             |
| Spring Boot 4.0.1  | Framework principal   |
| Spring Data JPA    | Persistência          |
| MySQL              | Banco de dados        |
| Flyway             | Migrações versionadas |
| Selenium (Firefox) | Web scraping          |
| Spring Scheduler   | Job agendado diário   |
| Maven              | Build                 |

---

## 📁 Estrutura do Projeto

```
com.bertolini.price_tracker_api
├── controller/
│   ├── PriceController.java
│   ├── ProductController.java
│   └── UserController.java
│   
├── domain/
│   ├── Price.java
│   ├── Product.java
│   ├── ShopType.java
│   └── User.java
│
├── dto/
│   ├── price/
│   ├── product/
│   └── user/
│
├── exception/
│   └── InvalidProductException.java
│
├── infrastructure/
│   ├── schedule/
│   │   └── PriceScheduler.java          ← diário via @Scheduled
│   │
│   ├── scraping/
│   │   ├── Scraper.java                 ← WebDriver gerenciado pelo Spring
│   │   ├── XpathRegistry.java           ← seletores centralizados por loja
│   │   └── PriceTransformer.java        ← converte texto para BigDecimal
│   │
│   └── web/
│       └── GlobalExceptionHandler.java
│
├── repository/
└── service/
    ├── price/
    ├── product/
    ├── scraping/
    └── user/
```

---

## 🏪 Lojas Suportadas

| ShopType        | Suporte                                     |
|-----------------|---------------------------------------------|
| `KALUNGA`       | ✅ XPath gerenciado internamente             |
| `MERCADO_LIVRE` | ✅ XPath gerenciado internamente             |
| `OTHER`         | ⚙️ XPath customizado fornecido pelo usuário |

---

## Endpoints

### Usuários — `/user`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/user` | Cadastra novo usuário |
| `GET` | `/user` | Lista usuários (paginado) |
| `GET` | `/user/{id}` | Retorna usuário por ID |
| `PUT` | `/user` | Atualiza dados do usuário |
| `DELETE` | `/user/{id}` | Remove usuário |

### Produtos — `/user/{userId}/product`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/user/{userId}/product` | Cadastra produto e realiza scraping inicial |
| `GET` | `/user/{userId}/product` | Lista produtos do usuário (paginado) |
| `PUT` | `/user/{userId}/product/{productId}` | Atualiza nome ou targetPrice |
| `DELETE` | `/user/{userId}/product/{productId}` | Remove produto e histórico |

### Preços — `/user/{userId}/product/{productId}/price`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/user/{userId}/product/{productId}/price` | Retorna histórico de preços paginado |

---

## Exemplo de Uso

**Cadastrar produto:**
```json
POST /user/1/product

{
  "name": "Monitor HP S3 Pro",
  "url": "https://www.kalunga.com.br/prod/...",
  "type": "KALUNGA",
  "targetPrice": 1200.00
}
```

**Resposta — 201 Created:**
```json
{
  "id": 5,
  "name": "Monitor HP S3 Pro",
  "url": "https://www.kalunga.com.br/prod/...",
  "targetPrice": 1200.00,
  "createdAt": "2026-03-02T19:30:00"
}
```

**Consultar histórico de preços:**
```
GET /user/1/product/5/price?page=0&size=10
```

---

## ⚙️ Configuração

### Pré-requisitos

- Java 21+
- MySQL
- Firefox instalado (para o Selenium)
- Maven 3.8+

### application.properties

```properties
spring.application.name=price-tracker-api
spring.web.error.include-stacktrace=never
spring.profiles.active=local
spring.jackson.property-naming-strategy=SNAKE_CASE
```
### application-local.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/price_tracker_db
spring.datasource.username=<seu_usuário>
spring.datasource.password=<sua_senha>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
### Executando
```bash
mvn spring-boot:run
```

O Flyway aplicará as migrações automaticamente. A API estará disponível em `http://localhost:8080`

OBS: É necessário entrar no mysql e fazer login 
```shell
mysql -u <seu_usuário> -p 
<sua_senha>
```
E criar a base de dados contida em `resources/db/init/init_database.sql`

```mysql
CREATE DATABASE price_tracker_db;
```

---

## Decisões Técnicas

**XpathRegistry centralizado** — os seletores de cada loja ficam em um único componente. Quando um site atualiza o HTML, basta alterar em um ponto sem tocar no restante do código. O XPath é resolvido e persistido no produto no cadastro, evitando consultas repetidas a cada scraping.

**Ciclo de vida do WebDriver** — O `ScrapingService` abre e fecha o driver por lote de produtos.

**Persistência antes do scraping** — o produto é salvo no banco antes do scraping para garantir que o `Price` sempre tenha um `product_id` válido. Se o scraping falhar, o produto é removido e o usuário recebe HTTP 400

**GlobalExceptionHandler** — `@RestControllerAdvice` centraliza o tratamento de erros, cobrindo validação de campos, entidades não encontradas, JSON mal formado e falhas de scraping

---

## Próximos Passos

- [ ] Autenticação e autorização com Spring Security + JWT
- [ ] Alertas por e-mail via Spring Mail quando o preço atingir o targetPrice
- [ ] Suporte a novas lojas famosas
- [ ] Testes unitários e de integração

---