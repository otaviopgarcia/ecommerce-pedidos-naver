# Sistema de Gestão de Pedidos — E-commerce 🛒

Projeto integrador da Unidade Curricular **Desenvolvimento Back-end** do Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas (CSTADS601) — Faculdade de Tecnologia SENAI "Antonio Adolpho Lobbe".

---

## 👥 Equipe / Squad

| Nome                        | GitHub                                                                                       | Papel no Projeto       |
| --------------------------- | -------------------------------------------------------------------------------------------- | ---------------------- |
| **Otávio Pinheiro Garcia**  | [@otaviopgarcia](https://www.google.com/url?sa=E&amp;q=https%3A%2F%2Fgithub.com%2Fotaviopgarcia) | Desenvolvedor Back-End |
| **Nykolas Guimarães Isler** | [@NykolasDev](https://www.google.com/url?sa=E&amp;q=https%3A%2F%2Fgithub.com%2FNykolasDev)       | Desenvolvedor Back-End |

---

## 📝 Descrição do Desafio

Construção de um **Sistema de Gestão de Pedidos para um E-commerce** completo, robusto e escalável. O projeto aplica de forma prática e incremental os conceitos da **Programação Orientada a Objetos (POO)** em Java, versionamento profissional com Git/GitHub, testes automatizados (unitários e de integração com cobertura mínima de 70%), persistência de dados e exposição de endpoints por meio de uma API RESTful utilizando Spring Boot, integrada a um pipeline de CI/CD automatizado.

---

## ⚙️ Funcionalidades Previstas

* [x] **Cadastro e gerenciamento de produtos** (controle de estoque, preço e status ativo/inativo)
* [x] **Cadastro e gerenciamento de clientes e funcionários** (hierarquia abstrata a partir de `Pessoa`)
* [x] **Criação e gerenciamento de pedidos** (adição dinâmica de itens e cálculo automático de subtotais e totais)
* [x] **Processamento de pagamentos** (hierarquia polimórfica para Cartão de Crédito, Boleto Bancário e Pix)
* [ ] **Testes automatizados** (unitários e de integração com cobertura de no mínimo 70%)
* [ ] **Pipeline de CI/CD** (GitHub Actions para automação de testes e builds)
* [ ] **API REST** (Spring Boot para integração com front-end)

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Gerenciador de Build:** Apache Maven
* **Versionamento:** Git &amp; GitHub
* *(Próximas tecnologias: JUnit 5, Spring Boot, Spring Data JPA, H2/PostgreSQL Database, GitHub Actions)*

---

## 📂 Estrutura de Pastas do Repositório

O repositório foi organizado em dois módulos principais para isolar as responsabilidades:

```
ecommerce-pedidos-naver/
├── back/                    # Módulo Back-End (Java + Maven)
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/ecommerce/pedidos/naver/
│   │   │           ├── modelo/                  # Entidades de domínio (Produto, Cliente, Funcionario, Pedido, etc.)
│   │   │           │   └── pagamento/           # Hierarquia FormaPagamento, CartaoCredito, Boleto, Pix
│   │   │           └── Aplicacao.java           # Classe de execução e simulação de cenários
│   │   └── test/
│   ├── pom.xml              # Dependências e configurações do Maven
│   └── .gitignore           # Filtros do Git para o back-end
└── front/                   # Módulo Front-End

```

---

## 🚀 Como Rodar o Projeto

1. **Clonar o repositório:**  
```  
git clone https://github.com/otaviopgarcia/ecommerce-pedidos-naver.git  
```
2. **Abrir a pasta do projeto Java:**  
  * Abra **apenas a pasta `back`** no VS Code ou IntelliJ IDEA para que a IDE reconheça corretamente a raiz do Maven (`pom.xml`).
3. **Executar a aplicação:**  
  * Execute o método `main` localizado no arquivo `com.ecommerce.pedidos.naver.Aplicacao`.

---

## 📈 Regras de Negócio &amp; Constantes Comercial (Aula 03)

As regras comerciais e financeiras foram encapsuladas na classe utilitária de suporte a pedidos:

* **Valor de frete por quilo:** `R$ 7,50` (cobrado por quilo iniciado, arredondando frações para cima).
* **Frete mínimo:** `R$ 15,00` (aplicado para compras leves).
* **Taxa de desconto padrão:** `10% (0.10)` sobre o subtotal do pedido.
* **Teto limite de desconto:** `R$ 50,00` (desconto máximo por pedido).
* **Valor para frete grátis:** `R$ 300,00` (pedidos acima deste valor isentam o frete).

---

## 🧪 Encapsulamento &amp; Experimento do BigDecimal (Aula 05)

Realizamos a blindagem dos atributos por meio de getters/setters com validação (lançando `IllegalArgumentException` em dados inválidos, como preços ou estoques negativos) e a migração dos valores monetários de `double` para **`BigDecimal`** na classe `Produto.java`:

* **Quantidade de arquivos que quebraram:** `3 arquivos` (`ItemPedido.java`, `Pedido.java` e `Aplicacao.java`).
* **O que esses arquivos tinham em comum:** Eram as únicas classes do sistema que realizavam cálculos matemáticos diretos (somas e multiplicações) com o preço do produto.
* **Conclusão da Equipe:** O experimento comprovou a eficácia do encapsulamento. As classes que consumiam o preço apenas para exibição (como o restante do sistema) continuaram compilando sem alterações. O impacto ficou isolado nas entidades responsáveis pelas operações financeiras.

---

## 🧬 Herança, Polimorfismo e Recusa de Herança (Aula 06)

### 1\. Hierarquias Implementadas

* **Hierarquia de Pessoas:** Classe abstrata `Pessoa` estendida por `Cliente` e `Funcionario`, reutilizando validações de nome e documento e definindo a identificação polimórfica via `getIdentificacao()`.
* **Hierarquia de Pagamentos:** Classe abstrata `FormaPagamento` estendida por `CartaoCredito`, `Boleto` e `Pix`, unificando a gestão de valores e implementando o método abstrato `processar()`.

### 2\. Registro de Decisões de Recusa de Herança

* **Proposta A — `CarrinhoDeCompras extends ArrayList
