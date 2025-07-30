# 🛒 CloudCart - API REST de E-commerce

> Este projeto faz parte do [Desafio de E-commerce do Roadmap.sh](https://roadmap.sh/projects/ecommerce-api)
Uma API REST robusta e escalável para E-commerce desenvolvida com **Spring Boot**.

## ✨ Funcionalidades

- ✅ Autenticação segura com **JWT**
- 💳 Integração com **Stripe** para pagamentos
- ☁️ Upload de imagens com **Cloudinary**
- 🛍️ Carrinho de compras, pedidos, gerenciamento de produtos e estoque
- 🧾 Validação de entrada e tratamento estruturado de exceções
- ⚙️ Rotas administrativas para gerenciamento de produtos e inventário

## 📚 Tecnologias Utilizadas

- Java 17 + Spring Boot 3
- PostgreSQL
- Stripe API
- Cloudinary SDK
- JWT
- Maven

## 🚀 Começando

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/cloudcart.git

# Construa o projeto
./mvnw clean install

# Execute a aplicação
./mvnw spring-boot:run
````

## 📂 Estrutura de Pastas

```
src
├── controller
├── dto
├── entity
├── repository
├── service
└── security
```

## 🧪 Testes

```bash
./mvnw test
```

## 🌱 Seed de Dados

Para facilitar os testes locais, foi adicionado um **Seeder** que popula o banco com:

- 2 usuários (admin e padrão)
- 10 produtos de exemplo

### 🔧 Como rodar o seed

Execute a aplicação com o argumento `--seed`:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--seed
````

> ⚠️ O seed **só será executado** se os repositórios de usuários e produtos estiverem vazios.

### 👤 Usuários criados

| Tipo   | Email                                     | Senha  |
| ------ | ----------------------------------------- | ------ |
| Admin  | [admin@email.com](mailto:admin@email.com) | 123456 |
| Padrão | [user@email.com](mailto:user@email.com)   | 123456 |


