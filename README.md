# 🛒 CloudCart - API REST de E-commerce

> Este projeto faz parte do [Desafio de E-commerce do Roadmap.sh](https://roadmap.sh/projects/ecommerce-api)  
> Uma API REST robusta, escalável e moderna para e-commerce, construída com **Spring Boot**.

---

## ✨ Funcionalidades

- ✅ Autenticação segura com **JWT**
- 💳 Integração com **Stripe** para pagamentos online
- ☁️ Upload e gerenciamento de imagens com **Cloudinary**
- 🛍️ Carrinho de compras, pedidos, gerenciamento de estoque e produtos
- 🧾 Validação de entrada e tratamento centralizado de exceções
- ⚙️ Rotas administrativas exclusivas para admins
- 📦 API de Checkout com cancelamento de pagamentos
- 📈 Documentação interativa com **Swagger**

---

## 📚 Tecnologias Utilizadas

- **Java 17 + Spring Boot 3**
- **PostgreSQL** (via [Supabase](https://supabase.com))
- **Stripe API** para pagamentos
- **Cloudinary SDK** para imagens
- **JWT** para autenticação
- **MapStruct** para mapeamento de DTOs
- **Maven** como gerenciador de dependências

---

## 🚀 Como Iniciar o Projeto

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/cloudcart.git

# Acesse o diretório
cd cloudcart

# Construa o projeto
./mvnw clean install

# Execute a aplicação localmente
./mvnw spring-boot:run
````

A aplicação estará disponível em: `http://localhost:8080`

---

## 📂 Estrutura de Pastas

```plaintext
CloudCart/
├── src/
│   ├── main/
│   │   ├── java/com/liamfer/CloudCart/
│   │   │   ├── config/               # Configurações de segurança, JWT, Cloudinary, Stripe, Swagger etc.
│   │   │   ├── controller/           # Controllers REST (produtos, carrinho, pagamentos, auth...)
│   │   │   ├── dto/                  # Objetos de transferência de dados (DTOs)
│   │   │   ├── entity/               # Entidades JPA (User, Product, Order...)
│   │   │   ├── enums/                # Enums usados no sistema
│   │   │   ├── exceptions/           # Exceções personalizadas
│   │   │   ├── repository/           # Interfaces de acesso ao banco (Spring Data)
│   │   │   ├── service/              # Lógica de negócios
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── CloudCartApplication.java
│   │   │   └── Seeder.java           # Classe de seed de dados
│   │   └── resources/
│   │       ├── application.properties
│   │       └── banner.txt
│   └── test/
│       └── java/com/liamfer/CloudCart/
│           ├── CloudCartApplicationTests.java
│           └── ProductControllerTest.java
├── pom.xml
├── README.md
├── mvnw / mvnw.cmd
└── LICENSE
```

---

## 🧪 Testes

Você pode rodar os testes unitários e de integração com:

```bash
./mvnw test
```

---

## 🌱 Seed de Dados

Para facilitar os testes locais, um **Seeder** foi implementado que popula o banco automaticamente com:

* 👤 **2 usuários**: admin e padrão
* 📦 **10 produtos** de exemplo

### ▶️ Como rodar o seed

Execute a aplicação com o argumento `--seed`:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--seed
```

> ⚠️ O seed **só será executado** se os repositórios de usuários e produtos estiverem vazios.

### 👥 Usuários criados automaticamente

| Tipo   | Email                                     | Senha  |
| ------ | ----------------------------------------- | ------ |
| Admin  | [admin@email.com](mailto:admin@email.com) | 123456 |
| Padrão | [user@email.com](mailto:user@email.com)   | 123456 |

---

## 🔐 Segurança

* Todas as rotas protegidas exigem **token JWT** no header `Authorization: Bearer <token>`.
* Admins possuem permissões para gerenciar produtos, usuários e pedidos.
* Usuários padrão só podem interagir com seu próprio carrinho, pedidos e perfil.

---

## 📘 Documentação da API

Acesse a documentação interativa gerada pelo Swagger:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📦 Banco de Dados

Este projeto utiliza **PostgreSQL** via [Supabase](https://supabase.com), uma alternativa open-source ao Firebase.
Você pode configurar sua instância do Supabase e atualizar os dados de acesso no `application.properties`.

---
