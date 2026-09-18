# Sistema de Gestão de Pedidos — E-commerce 🛒

Projeto integrador da Unidade Curricular **Desenvolvimento Back-end** do Curso Superior de Tecnologia em Análise e Desenvolvimento de Sistemas (CSTADS601) — Faculdade de Tecnologia SENAI "Antonio Adolpho Lobbe".

## 👥 Equipe / Squad

| Nome                        | GitHub                                             | Papel no Projeto       |
| :-------------------------- | :------------------------------------------------- | :--------------------- |
| **Otávio Pinheiro Garcia**  | [@otaviopgarcia](https://github.com/otaviopgarcia) | Desenvolvedor Back-End |
| **Nykolas Guimarães Isler** | [@NykolasDev](https://github.com/NykolasDev)       | Desenvolvedor Back-End |

## 📝 Descrição do Desafio

Construção de um **Sistema de Gestão de Pedidos para um E-commerce** completo, robusto e escalável.

O projeto aplica, de forma prática e incremental, conceitos de **Programação Orientada a Objetos (POO)** em Java, versionamento profissional com Git/GitHub, testes automatizados (unitários e de integração com cobertura mínima de 70%), persistência de dados e exposição de endpoints por meio de uma API RESTful utilizando Spring Boot, integrada a um pipeline de CI/CD automatizado.

## ⚙️ Funcionalidades Previstas

* [x] **Cadastro e gerenciamento de produtos** — controle de estoque, preço com `BigDecimal` e status ativo/inativo.
* [x] **Cadastro e gerenciamento de clientes e funcionários** — hierarquia abstrata a partir de `Pessoa`.
* [x] **Criação e gerenciamento de pedidos e itens** — relacionamento de composição `Pedido ◆── ItemPedido`, com associação a `Cliente` e `Produto`.
* [x] **Processamento de pagamentos** — hierarquia polimórfica para Cartão de Crédito, Boleto Bancário e Pix.
* [ ] **Testes automatizados** — testes unitários e de integração com cobertura mínima de 70%.
* [ ] **Pipeline de CI/CD** — GitHub Actions para automação de testes e builds.
* [ ] **API REST** — Spring Boot para integração com o front-end.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Gerenciador de Build:** Apache Maven
* **Versionamento:** Git & GitHub
* **Modelagem:** UML / draw.io
* **Próximas tecnologias:** JUnit 5, Spring Boot, Spring Data JPA, H2/PostgreSQL e GitHub Actions.

## 📂 Estrutura de Pastas do Repositório

O repositório foi organizado em módulos e diretórios bem definidos:

```text
ecommerce-pedidos-naver/
├── docs/                    # Diagramas UML (PNG + .drawio) e documentação técnica
│   ├── diagrama-de-classes.png
│   └── diagrama-de-classes.drawio
├── back/                    # Módulo Back-End (Java + Maven)
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/ecommerce/pedidos/naver/
│   │   │           ├── modelo/                  # Entidades
│   │   │           │   └── pagamento/           # Hierarquia de pagamentos
│   │   │           ├── util/                    # Utilitários e constantes
│   │   │           └── Aplicacao.java           # Execução e testes de integridade
│   │   └── test/
│   ├── pom.xml              # Dependências do Maven
│   └── .gitignore
└── front/                   # Módulo Front-End
```

## 🚀 Como Rodar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/otaviopgarcia/ecommerce-pedidos-naver.git
```

### 2. Abrir a pasta do projeto Java

Abra **apenas a pasta `back`** no VS Code ou IntelliJ IDEA para que a IDE reconheça corretamente a raiz do projeto Maven (`pom.xml`).

### 3. Executar a aplicação

Execute o método `main` localizado em:

```text
com.ecommerce.pedidos.naver.Aplicacao
```

## 📈 Regras de Negócio e Constantes Comerciais — Aula 03

As regras comerciais e financeiras estão encapsuladas na classe utilitária de suporte:

* **Valor de frete por quilo:** `R$ 7,50` por quilo iniciado, com arredondamento para cima utilizando `Math.ceil`.
* **Frete mínimo:** `R$ 15,00`, garantido por meio de `Math.max`.
* **Taxa de desconto padrão:** `10% (0.10)` sobre o subtotal.
* **Limite máximo de desconto:** `R$ 50,00` por pedido, utilizando `Math.min`.
* **Valor para frete grátis:** `R$ 300,00`. Pedidos acima desse valor são isentos de frete.

## 🧪 Encapsulamento e Experimento do BigDecimal — Aula 05

Realizamos a blindagem dos atributos por meio de getters e setters com validação, lançando `IllegalArgumentException` para dados inválidos. Também foi realizada a migração dos valores monetários de `double` para `BigDecimal` na classe `Produto.java`.

* **Quantidade de arquivos que quebraram:** 3 arquivos (`ItemPedido.java`, `Pedido.java` e `Aplicacao.java`).
* **Conclusão da equipe:** O experimento comprovou a eficácia do encapsulamento. As classes que utilizavam o preço apenas para exibição continuaram compilando sem alterações, isolando a mudança apenas nos pontos em que ocorriam cálculos financeiros.

## 🧬 Herança, Polimorfismo e Recusa de Herança — Aula 06

* **Hierarquia de Pessoas:** classe abstrata `Pessoa`, estendida por `Cliente` e `Funcionario`.
* **Hierarquia de Pagamentos:** classe abstrata `FormaPagamento`, estendida por `CartaoCredito`, `Boleto` e `Pix`.
* **Recusa de Herança:**

  * `CarrinhoDeCompras extends ArrayList` — foi utilizada composição.
  * `PedidoCancelado extends Pedido` — foi utilizado o `Enum SituacaoPedido`.
  * `ClienteVip extends Cliente` — a característica foi mantida como atributo/categoria.

## 🔗 Relacionamentos entre Classes de Domínio — Aula 07

Modelamos e implementamos as conexões do domínio com restrições de ciclo de vida e multiplicidade.

### 1. Mapeamento dos Relacionamentos

* **Composição (`Pedido ◆──► ItemPedido`) | Multiplicidade: `1..*`**

  O `Pedido` é responsável pelo ciclo de vida dos itens. Ele cria os próprios `ItemPedido` internamente por meio do método `adicionarItem(Produto produto, int quantidade)`.

  A coleção interna é protegida no getter por meio de `Collections.unmodifiableList(itens)`, evitando modificações externas e garantindo o lançamento de `UnsupportedOperationException` quando houver tentativa de alteração direta.

* **Associação obrigatória (`Pedido ──► Cliente`) | Multiplicidade: `1`**

  Todo pedido exige um cliente válido. Valores `null` são recusados no construtor por meio de `IllegalArgumentException`.

* **Associação obrigatória (`ItemPedido ──► Produto`) | Multiplicidade: `1`**

  Cada item referencia um produto do catálogo e registra, de forma imutável, o `precoPraticado` no momento da compra.

* **Associação opcional (`Pedido ──► FormaPagamento`) | Multiplicidade: `0..1`**

  A forma de pagamento é associada ao pedido no momento da finalização/pagamento.

### 2. Decisão de Negócio: Adição de Produto Repetido no Pedido

* **Decisão da Squad:** quando o mesmo produto é adicionado novamente ao pedido, o sistema soma a quantidade ao item já existente em vez de criar uma nova linha.
* **Justificativa:** mantém o recibo mais enxuto e organizado, recalculando o subtotal de forma transparente.

### 3. Validações e Testes de Integridade

O arquivo `Aplicacao.java` foi atualizado para comprovar as regras e restrições do modelo:

* **Pedido sem itens:** impede o pagamento/finalização de um pedido sem itens, respeitando a multiplicidade `1..*`.
* **Validação de estoque:** lança `IllegalStateException` quando a quantidade solicitada é maior que a quantidade disponível em estoque no `Produto`.
* **Proteção da coleção:** impede chamadas como `pedido.getItens().clear()`, garantindo que toda alteração passe pelos métodos de negócio do `Pedido`.

## 🗺️ Roadmap do Projeto — por Aula

|  Aula  | Entrega                                                                                    |   Status  |
| :----: | :----------------------------------------------------------------------------------------- | :-------: |
| **01** | Repositório criado, estruturado, com README e commit inicial                               | Concluído |
| **02** | Fluxo de branches e primeiro Pull Request revisado                                         | Concluído |
| **03** | Classe utilitária (`PedidoUtils`) e constantes de negócio                                  | Concluído |
| **04** | Classes de domínio inicial (`Produto`, `Cliente`, `Pedido`, `ItemPedido`)                  | Concluído |
| **05** | Encapsulamento, validações, `Pessoa` abstrata e `BigDecimal`                               | Concluído |
| **06** | Hierarquias de herança (`Funcionario`, `FormaPagamento`: `Pix`, `Boleto`, `CartaoCredito`) | Concluído |
| **07** | Relacionamentos entre classes do domínio (Associação, Agregação e Composição)              | Concluído |
| **08** | Módulo de pagamento polimórfico                                                            |  Em breve |
| **09** | Tratamento de exceções customizadas                                                        |  Em breve |
| **10** | Suíte de testes unitários (JUnit)                                                          |  Em breve |
| **11** | Suíte de testes de integração + relatório de cobertura                                     |  Em breve |
| **12** | Persistência: conexão com banco de dados, Create e Read                                    |  Em breve |
| **13** | Persistência: Update, Delete e padrão DAO/Repository                                       |  Em breve |
| **14** | Migração para Spring Boot                                                                  |  Em breve |
| **15** | API RESTful + Pipeline CI/CD com GitHub Actions                                            |  Em breve |
| **16** | Entrega final, documentação e apresentação                                                 |  Em breve |

## 🤝 Combinado da Equipe — Ética e Convivência

1. **Revisão ética e respeitosa:** todos os Pull Requests devem ser revisados detalhadamente por outro membro da squad, com foco em melhorias técnicas no código e sem avaliações pessoais.

2. **Proibição de commits diretos na `main`:** toda funcionalidade deve ser desenvolvida em uma branch específica (`feature/nome-da-funcionalidade`) e integrada obrigatoriamente por meio de Pull Request.

3. **Resolução transparente de conflitos:** conflitos de merge devem ser resolvidos por meio de conversa direta entre os autores das alterações, garantindo que o código de nenhum colega seja sobrescrito indevidamente.

## 📑 Licença

Projeto acadêmico — Faculdade de Tecnologia SENAI "Antonio Adolpho Lobbe".
