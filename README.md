# 🛒 CloudCart - API REST de E-commerce

Uma API REST robusta e escalável para E-commerce desenvolvida com **Spring Boot**.

## ✨ Funcionalidades

- ✅ Autenticação segura com **JWT**
- 💳 Integração com **Stripe** para pagamentos
- ☁️ Upload de imagens com **Cloudinary**
- 🛍️ Carrinho de compras, pedidos, gerenciamento de produtos e estoque
- 🧾 Validação de entrada e tratamento estruturado de exceções
- ⚙️ Rotas administrativas para gerenciamento de produtos e inventário

## 📚 Tecnologias Utilizadas

- Java 21 + Spring Boot 3
- PostgreSQL
- Stripe API
- Cloudinary SDK
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
