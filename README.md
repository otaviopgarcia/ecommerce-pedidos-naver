# Sistema de Gestão de Pedidos — E-commerce

> Projeto integrador da Unidade Curricular **Desenvolvimento Back-end**  
> Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas — Turma **CSTADS601**

## 📋 Equipe / Squad

| Nome | Papel no Projeto |
|------|------------------|
| Otávio Pinheiro Garcia | Desenvolvedor Back-end |
| Nykolas Guimarães Isler | Desenvolvedor Back-end |
| _(integrante 3)_ | _(a definir)_ |
| _(integrante 4)_ | _(a definir)_ |

---

## 📝 Descrição do desafio

A equipe recebeu a demanda de construir um **sistema de gestão de pedidos para um e-commerce**, contemplando cadastro de produtos, clientes, pedidos e processamento de pagamentos. O projeto será desenvolvido ao longo do semestre, evoluindo desde os conceitos fundamentais de orientação a objetos até a construção de uma API REST completa com testes automatizados e pipeline de CI/CD.

---

## ✅ Funcionalidades previstas

- [ ] Cadastro e gerenciamento de produtos
- [ ] Cadastro e gerenciamento de clientes
- [ ] Criação e gerenciamento de pedidos
- [ ] Processamento de pagamentos (cartão, boleto, Pix)
- [ ] Testes automatizados (unitários e de integração)
- [ ] Pipeline de CI/CD
- [ ] API REST para consumo por um front-end

---

## 🛠️ Tecnologias

- **Java** (versão 17+)
- **Maven** (gerenciamento de dependências e build)
- **Git / GitHub** (versionamento e colaboração)
- **VS Code** (IDE utilizada pela equipe)

> Tecnologias que serão adicionadas ao longo do semestre: **JUnit**, **Spring Boot**, **Banco de Dados**, **GitHub Actions**.

---

## 📁 Estrutura de pastas (atual)

```text
ecommerce-pedidos-naver/
├── .vscode/                     # Configurações da IDE
├── back/                        # Código back-end (Java)
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/senai/ecommerce/
│   │   │           ├── modelo/       # Classes de domínio (Produto, Cliente, Pedido, etc.)
│   │   │           ├── servico/      # Regras de negócio
│   │   │           ├── repositorio/  # Persistência e DAO
│   │   │           └── util/         # Classes utilitárias
│   │   └── test/
│   │       └── java/
│   │           └── com/senai/ecommerce/
│   └── pom.xml
├── front/                       # Código front-end (a ser desenvolvido)
├── README.md
└── .gitignore
🚀 Como rodar o projeto
(A preencher a partir das próximas aulas, conforme o projeto evoluir.)

Por enquanto, o back-end pode ser compilado com:

bash
cd back
mvn clean install
