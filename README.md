# Sistema de Gestão de Pedidos — E-commerce 🛒

Projeto integrador da Unidade Curricular **Desenvolvimento Back-end** do Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas (CSTADS601) — Faculdade de Tecnologia SENAI "Antonio Adolpho Lobbe".

---

## 👥 Equipe / Squad

| Nome                        | GitHub                                                   | Papel no Projeto       |
| --------------------------- | -------------------------------------------------------- | ---------------------- |
| **Otávio Pinheiro Garcia**  | [@otaviopgarcia](https://github.com/otaviopgarcia)       | Desenvolvedor Back-End |
| **Nykolas Guimarães Isler** | [@NykolasDev](https://github.com/NykolasDev)             | Desenvolvedor Back-End |

---

## 📝 Descrição do Desafio

Construção de um **Sistema de Gestão de Pedidos para um E-commerce** completo, robusto e escalável. O projeto aplica de forma prática e incremental os conceitos da **Programação Orientada a Objetos (POO)** em Java, versionamento profissional com Git/GitHub, testes automatizados (unitários e de integração, com cobertura mínima de 70%), persistência de dados e exposição de endpoints por meio de uma API RESTful com Spring Boot, integrada a um pipeline de CI/CD automatizado.

---

## ⚙️ Funcionalidades Previstas

* [x] **Cadastro e gerenciamento de produtos** (controle de estoque, preço com `BigDecimal` e status ativo/inativo)
* [x] **Cadastro e gerenciamento de clientes e funcionários** (hierarquia abstrata a partir de `Pessoa`)
* [x] **Criação e gerenciamento de pedidos e itens** (composição `Pedido ◆── ItemPedido`, associação com `Cliente` e `Produto`)
* [x] **Processamento de pagamentos polimórfico** (hierarquia e interface `ProcessadorPagamento` para Cartão de Crédito, Boleto, Pix e Dinheiro)
* [x] **Tratamento de exceções customizadas e resiliência** (hierarquia com `ECommerceException`, exceções com dados de negócio e eliminação de stack traces)
* [x] **Testes automatizados** (suíte de 22 testes unitários em JUnit 5 com cobertura de ~89% do domínio)
* [ ] **Pipeline de CI/CD** (GitHub Actions para automação de testes e builds)
* [ ] **API REST** (Spring Boot para integração com front-end)

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Gerenciador de build:** Apache Maven
- **Versionamento:** Git & GitHub
- **Modelagem:** UML / draw.io
- *Próximas tecnologias: JUnit 5, Spring Boot, Spring Data JPA, H2/PostgreSQL, GitHub Actions*

---

## 🚀 Como Rodar o Projeto

**Pré-requisitos:** JDK 17+ e Apache Maven instalados.

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/otaviopgarcia/ecommerce-pedidos-naver.git
   ```

2. **Abra apenas a pasta `back`** no VS Code ou IntelliJ IDEA, para que a IDE reconheça a raiz do Maven (`pom.xml`).

3. **Execute a aplicação:** rode o método `main` da classe `com.ecommerce.pedidos.naver.Aplicacao`.

---

## 📂 Estrutura de Pastas do Repositório

```
ecommerce-pedidos-naver/
├── docs/                    # Diagramas UML (PNG + .drawio) e documentação técnica
│   ├── diagrama-de-classes.png
│   └── diagrama-de-classes.drawio
├── back/                    # Módulo Back-End (Java + Maven)
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/ecommerce/pedidos/naver/
│   │   │           ├── excecao/     # Exceções de domínio (ECommerceException, EstoqueInsuficienteException, etc.)
│   │   │           ├── modelo/      # Entidades (Produto, Cliente, Funcionario, Pedido, ItemPedido)
│   │   │           │   └── pagamento/   # ProcessadorPagamento, FormaPagamento, Pix, Boleto, CartaoCredito, Dinheiro
│   │   │           ├── util/        # Utilitários e constantes (PedidoUtils)
│   │   │           └── Aplicacao.java   # Execução, testes polimórficos e tratamento de erros
│   │   └── test/
│   ├── pom.xml              # Dependências do Maven
│   └── .gitignore
└── front/                   # Módulo Front-End
```

---

## 📈 Regras de Negócio e Constantes Comerciais (Aula 03)

As regras comerciais e financeiras estão encapsuladas na classe utilitária `PedidoUtils`:

| Regra                    | Valor                | Implementação                                      |
| ------------------------ | -------------------- | -------------------------------------------------- |
| Frete por quilo          | R$ 7,50              | Por quilo iniciado, arredondando com `Math.ceil`   |
| Frete mínimo             | R$ 15,00             | Garantido via `Math.max`                           |
| Taxa de desconto padrão  | 10% (`0.10`)         | Aplicada sobre o subtotal                          |
| Teto de desconto         | R$ 50,00             | Desconto máximo por pedido, via `Math.min`         |
| Frete grátis             | Acima de R$ 300,00   | Pedidos acima desse valor isentam o frete          |

---

## 🧪 Encapsulamento e Experimento do BigDecimal (Aula 05)

Blindamos os atributos com getters/setters validados (lançando `IllegalArgumentException` em dados inválidos) e migramos os valores monetários de `double` para **`BigDecimal`** na classe `Produto`.

- **Arquivos que quebraram:** 3 (`ItemPedido.java`, `Pedido.java` e `Aplicacao.java`).
- **Conclusão da equipe:** o experimento comprovou a eficácia do encapsulamento. As classes que consumiam o preço apenas para exibição continuaram compilando sem alterações, isolando a mudança onde havia cálculos financeiros.

---

## 🧬 Herança, Polimorfismo e Recusa de Herança (Aula 06)

- **Hierarquia de pessoas:** classe abstrata `Pessoa`, estendida por `Cliente` e `Funcionario`.
- **Hierarquia de pagamentos:** classe abstrata `FormaPagamento`, estendida por `CartaoCredito`, `Boleto` e `Pix`.
- **Recusa de herança** (casos em que herdar seria a escolha errada):
  - `CarrinhoDeCompras extends ArrayList` → usada **composição**.
  - `PedidoCancelado extends Pedido` → usado o enum `SituacaoPedido`.
  - `ClienteVip extends Cliente` → mantido como atributo/categoria.

---

## 🔗 Relacionamentos entre Classes de Domínio (Aula 07)

Conexões do domínio modeladas com restrições rígidas de ciclo de vida e multiplicidade:

- **Composição (`Pedido ◆──► ItemPedido`, multiplicidade 1..\*):** o `Pedido` é dono do ciclo de vida dos itens. A coleção interna é protegida no getter com `Collections.unmodifiableList(itens)`, evitando modificações externas (`UnsupportedOperationException`).
- **Associações obrigatórias:** `Pedido ──► Cliente` (multiplicidade 1) e `ItemPedido ──► Produto` (multiplicidade 1).
- **Produto repetido:** ao adicionar novamente um produto ao mesmo pedido, o sistema **soma a quantidade no item já existente**.

---

## 🎭 Módulo de Pagamento Polimórfico (Aula 08)

Fluxo de pagamentos refatorado aplicando o **Princípio Aberto/Fechado (SOLID)** e eliminando dependências de classes concretas:

- **Interface `ProcessadorPagamento`:** contrato padronizado (`processar`, `getComprovante`, `getDescricao`).
- **Polimorfismo dinâmico:** `Pedido` interage exclusivamente com a interface, sem `if` ou `instanceof` para verificar o tipo de pagamento.
- **Polimorfismo estático (sobrecarga):** `adicionarItem(produto)` delega para `adicionarItem(produto, quantidade)`.
- **Prova de extensão:** a classe `Dinheiro` foi criada sem alterar nenhuma linha existente em `Pedido` ou nas outras formas de pagamento.

---

## 🚨 Tratamento de Exceções Customizadas e Resiliência (Aula 09)

Hierarquia de exceções de domínio que substitui *stack traces* genéricos por mensagens claras e dados estruturados:

- **Exceção-raiz `ECommerceException`:** classe base *checked*, estende `Exception`, com suporte a mensagem e encadeamento de causas (`Throwable cause`).
- **Exceções de domínio:**
  - `EstoqueInsuficienteException`: carrega o `Produto` e a `quantidadeSolicitada`.
  - `PagamentoRecusadoException`: armazena a forma de pagamento e o `motivo` da recusa.
  - `PedidoInvalidoException`: trata estados inconsistentes (adicionar itens a pedidos pagos, pagar sem itens).
  - `ClienteNaoEncontradoException`: lançada em buscas sem retorno.
- **Critério checked vs. unchecked:**
  - **Checked (`ECommerceException`):** situações de negócio previsíveis e recuperáveis.
  - **Unchecked (`IllegalArgumentException`):** violações de programação ou parâmetros nulos/negativos.
- **Anti-patterns eliminados:** nenhum `catch` vazio, nenhuma captura genérica de `Exception` e nenhuma chamada a `printStackTrace()`.

---

## 🗺️ Roadmap do Projeto (por Aula)

| Aula   | Entrega                                                                          | Status    |
| ------ | -------------------------------------------------------------------------------- | --------- |
| **01** | Repositório criado, estruturado, com README e commit inicial                     | Concluído |
| **02** | Fluxo de branches e primeiro Pull Request revisado                               | Concluído |
| **03** | Classe utilitária (`PedidoUtils`) e constantes de negócio                        | Concluído |
| **04** | Classes de domínio inicial (Produto, Cliente, Pedido, ItemPedido)                | Concluído |
| **05** | Encapsulamento, validações, `Pessoa` abstrata e `BigDecimal`                     | Concluído |
| **06** | Hierarquias de herança (Funcionario, FormaPagamento: Pix, Boleto, CartaoCredito) | Concluído |
| **07** | Relacionamentos entre classes do domínio (Associação, Agregação e Composição)    | Concluído |
| **08** | Módulo de pagamento polimórfico e interface `ProcessadorPagamento`               | Concluído |
| **09** | Tratamento de exceções customizadas                                              | Concluído |
| **10** | Suíte de testes unitários (JUnit 5) e Plano de Testes                            | Concluído |
| **11** | Suíte de testes de integração + relatório de cobertura                           | Em breve  |
| **12** | Persistência: conexão com banco de dados, Create e Read                          | Em breve  |
| **13** | Persistência: Update, Delete e padrão DAO/Repository                             | Em breve  |
| **14** | Migração para Spring Boot                                                        | Em breve  |
| **15** | API RESTful + Pipeline CI/CD com GitHub Actions                                  | Em breve  |
| **16** | Entrega final, documentação e apresentação                                       | Em breve  |

---

## 🤝 Combinado da Equipe (Ética e Convivência)

1. **Revisão ética e respeitosa:** todos os Pull Requests são revisados em detalhe por outro membro da squad, com foco em melhorias técnicas e sem avaliações pessoais.
2. **Proibição de commits diretos na `main`:** toda funcionalidade é desenvolvida em uma branch específica (`feature/nome-da-funcionalidade`) e integrada obrigatoriamente via Pull Request.
3. **Resolução transparente de conflitos:** conflitos de merge são resolvidos por conversa direta entre os autores das alterações, garantindo que o código de nenhum colega seja sobrescrito indevidamente.

---

## 📑 Licença

Projeto acadêmico — Faculdade de Tecnologia SENAI "Antonio Adolpho Lobbe".