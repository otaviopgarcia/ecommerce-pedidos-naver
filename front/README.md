# 🛒 E-Commerce NavEr - Sistema de Gestão de Pedidos

Projeto Integrador desenvolvido para o curso de Desenvolvimento de Software, englobando a construção da **API RESTful** no Back-End (Java / Spring Boot) e a **Interface de Usuário** no Front-End (React.js + Tailwind CSS).

---

## 🎨 Identidade Visual e UI

- **Paleta de cores:** exclusivamente **Laranja (`#F97316`)** e **Branco (`#FFFFFF`)**, garantindo um design limpo, moderno e de alto contraste, sem uso de tons de cinza ou azul.
- **Framework CSS:** Tailwind CSS.

---

## 🛠️ Tecnologias Utilizadas

### Front-End (`/front`)

- **React.js** (Vite)
- **Tailwind CSS**
- **React Router DOM** (roteamento de telas)
- **Context API** (gerenciamento do carrinho de compras e `localStorage`)

### Back-End (`/back`)

- **Java 17+** / **Maven**
- **Spring Boot** (Spring Web, Spring Data JPA)
- **Banco de dados:** H2 (desenvolvimento) / PostgreSQL (produção)

---

## 📁 Estrutura do Repositório

```text
ecommerce-pedidos-naver/
├── back/                      # Back-End Java / Spring Boot
│   ├── src/
│   └── pom.xml
├── front/                     # Front-End React.js
│   ├── src/
│   │   ├── components/        # Componentes reutilizáveis (Navbar)
│   │   ├── contexts/          # Estado do carrinho (CartContext)
│   │   ├── pages/             # Catalogo, GestaoProdutos, CarrinhoPage, Checkout, Pedidos
│   │   └── services/          # Camada de requisições / mock (api.js)
│   ├── package.json
│   └── tailwind.config.js
└── README.md                  # Documentação principal
```

---

## 📌 Funcionalidades

- **Catálogo:** busca por nome, filtro por categoria e adição de itens ao carrinho.
- **Gestão (Admin):** cadastro, edição e exclusão de produtos.
- **Carrinho de compras:** ajuste de quantidades, resumo financeiro e persistência local.
- **Checkout:** formulário completo e simulação de pagamento.
- **Meus Pedidos:** acompanhamento do histórico de compras.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- [Node.js](https://nodejs.org/) 18 ou superior
- [Java JDK](https://adoptium.net/) 17 ou superior
- [Maven](https://maven.apache.org/) 3.8 ou superior

### Front-End

1. Acesse a pasta do front-end:

```bash
   cd front
```

2. Instale as dependências:

```bash
   npm install
```

3. Execute em modo de desenvolvimento:

```bash
   npm run dev
```

4. Acesse no navegador: [http://localhost:5173](http://localhost:5173)

> **Nota:** por padrão, o front-end pode utilizar dados simulados (mock) definidos em `src/services/api.js`.

### Back-End

1. Acesse a pasta do back-end:

```bash
   cd back
```

2. Execute a aplicação:

```bash
   mvn spring-boot:run
```

3. A API ficará disponível em: [http://localhost:8080](http://localhost:8080)

> Com o banco H2, o console fica disponível em `http://localhost:8080/h2-console`.

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.