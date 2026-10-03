# Mapa de Exceções do Sistema

Entrega da **Aula 09** da Atividade Desafiadora. Este mapa define a hierarquia de exceções no papel **antes** de escrever a primeira classe de exceção. Ele também é o roteiro da suíte de testes da **Aula 10**: cada linha da Parte 3 vira um método com `assertThrows`.

## Identificação

| Campo              | Valor                                                |
| ------------------ | ---------------------------------------------------- |
| Equipe / Squad     | Otávio Pinheiro Garcia & Nykolas Guimarães Isler     |
| Branch             | `feature/tratamento-excecoes`                        |
| Nº do Pull Request | #1                                                   |
| Pacote de exceções | `com.ecommerce.pedidos.naver.excecao`                |

---

## Parte 1 — O critério da equipe

Regra geral adotada, única para o projeto inteiro. Quem consome o domínio não tem como adivinhar se cada método adotou uma regra diferente.

| Pergunta                                           | Resposta da equipe                                                                                                                                                                   |
| -------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Quando uma exceção será **checked**?               | Quando representar uma situação de negócio previsível e recuperável pela aplicação (ex.: estoque insuficiente, pagamento recusado), permitindo ação corretiva do usuário ou do sistema. |
| Quando será **unchecked**?                         | Quando indicar violação de contrato de método, parâmetro inválido ou erro de programação (ex.: argumento nulo, valor negativo, string vazia).                                         |
| Qual é a exceção-raiz do domínio?                  | `ECommerceException` (estende `java.lang.Exception` — checked).                                                                                                                      |
| Quem trata: o domínio ou a camada de apresentação? | O domínio valida e **lança** a exceção; a camada de apresentação (`Aplicacao` / Controller / UI) **captura e trata**, exibindo uma resposta amigável.                                 |
| O que fazemos quando não sabemos tratar?           | Encapsulamos a exceção em uma exceção de domínio, preservando a causa original (`cause`), e lançamos para a camada superior.                                                         |

---

## Parte 2 — Hierarquia de exceções do projeto

| Exceção                         | Herda de             | Checked? | Dados que carrega                               | Quem lança                                        |
| ------------------------------- | -------------------- | -------- | ----------------------------------------------- | ------------------------------------------------- |
| `ECommerceException`            | `Exception`          | Sim      | mensagem, causa (`Throwable`)                   | *(raiz — exceção base do domínio)*                |
| `EstoqueInsuficienteException`  | `ECommerceException` | Sim      | `Produto produto`, `int quantidadeSolicitada`   | `Produto.baixarEstoque`, `Pedido.adicionarItem`   |
| `PagamentoRecusadoException`    | `ECommerceException` | Sim      | `String formaPagamento`, `String motivo`        | `Pedido.pagar`                                    |
| `PedidoInvalidoException`       | `ECommerceException` | Sim      | `String numeroPedido`, `String motivo`          | `Pedido.adicionarItem`, `Pedido.pagar`            |
| `ClienteNaoEncontradoException` | `ECommerceException` | Sim      | `String identificador`                          | `ClienteService` / `Aplicacao`                    |

**Exceções da linguagem reaproveitadas (em vez de criar as nossas):**

| Exceção                    | Onde usamos                                                                       | Por quê                                                                                                       |
| -------------------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| `IllegalArgumentException` | Construtores e setters de `Produto`, `Pessoa`, `Cliente`, `Pedido`, `ItemPedido`  | Barrar parâmetros nulos, vazios ou numéricos fora do intervalo válido (`preço < 0`, `quantidade <= 0`).       |
| `IllegalStateException`    | Validações estáticas de transição de estado de objetos                            | Indicar que um objeto está em estado incompatível com a operação solicitada.                                  |

---

## Parte 3 — Mapa de validações por fluxo

A coluna **Exceção esperada** foi preenchida antes da execução; **Obtido** registra o resultado real. Prever e depois conferir é o que separa teste de tentativa.

### Cadastro de produto

| #   | Entrada / situação | Exceção esperada           | Mensagem exibida                               | Obtido                     | OK  |
| --- | ------------------ | -------------------------- | ---------------------------------------------- | -------------------------- | --- |
| 1   | Nome vazio         | `IllegalArgumentException` | Nome do produto nao pode ser vazio.            | `IllegalArgumentException` | SIM |
| 2   | Preço negativo     | `IllegalArgumentException` | Preco nao pode ser nulo nem negativo.          | `IllegalArgumentException` | SIM |
| 3   | Preço zero / nulo  | `IllegalArgumentException` | Preco nao pode ser nulo nem negativo.          | `IllegalArgumentException` | SIM |
| 4   | Estoque negativo   | `IllegalArgumentException` | Quantidade em estoque nao pode ser negativa.   | `IllegalArgumentException` | SIM |
| 5   | Código nulo/vazio  | `IllegalArgumentException` | Codigo nao pode ser vazio.                     | `IllegalArgumentException` | SIM |

### Cadastro de cliente

| #   | Entrada / situação      | Exceção esperada           | Mensagem exibida                      | Obtido                     | OK  |
| --- | ----------------------- | -------------------------- | ------------------------------------- | -------------------------- | --- |
| 1   | Nome vazio              | `IllegalArgumentException` | Nome nao pode ser vazio.              | `IllegalArgumentException` | SIM |
| 2   | E-mail sem @ / inválido | `IllegalArgumentException` | E-mail deve conter o caractere '@'.   | `IllegalArgumentException` | SIM |
| 3   | CPF nulo / vazio        | `IllegalArgumentException` | CPF nao pode ser vazio.               | `IllegalArgumentException` | SIM |
| 4   | Cliente nulo no Pedido  | `IllegalArgumentException` | Cliente do pedido nao pode ser nulo.  | `IllegalArgumentException` | SIM |

### Pedido

| #   | Entrada / situação               | Exceção esperada               | Mensagem exibida                                                                      | Obtido                         | OK  |
| --- | -------------------------------- | ------------------------------ | ------------------------------------------------------------------------------------- | ------------------------------ | --- |
| 1   | Quantidade zero ou negativa      | `IllegalArgumentException`     | Quantidade deve ser maior que zero.                                                   | `IllegalArgumentException`     | SIM |
| 2   | Produto com estoque insuficiente | `EstoqueInsuficienteException` | Estoque insuficiente de [Nome]: disponível X, solicitado Y                            | `EstoqueInsuficienteException` | SIM |
| 3   | Produto null                     | `IllegalArgumentException`     | Produto nao pode ser nulo.                                                            | `IllegalArgumentException`     | SIM |
| 4   | Adicionar item a pedido já pago  | `PedidoInvalidoException`      | Pedido [N] inválido: Não é possível adicionar itens a um pedido que já está PAGO.     | `PedidoInvalidoException`      | SIM |
| 5   | Número de pedido vazio           | `IllegalArgumentException`     | Numero do pedido nao pode ser vazio.                                                  | `IllegalArgumentException`     | SIM |

### Pagamento

| #   | Entrada / situação               | Exceção esperada             | Mensagem exibida                                                      | Obtido                       | OK  |
| --- | -------------------------------- | ---------------------------- | --------------------------------------------------------------------- | ---------------------------- | --- |
| 1   | Processador null                 | `IllegalArgumentException`   | Processador de pagamento é obrigatório.                               | `IllegalArgumentException`   | SIM |
| 2   | Pedido sem itens                 | `PedidoInvalidoException`    | Pedido [N] inválido: Não é possível pagar um pedido sem itens.        | `PedidoInvalidoException`    | SIM |
| 3   | Pagamento recusado               | `PagamentoRecusadoException` | Pagamento por [Forma] recusado: Transação recusada pela operadora...  | `PagamentoRecusadoException` | SIM |
| 4   | Pedido já pago                   | `PedidoInvalidoException`    | Pedido [N] inválido: O pedido já foi pago anteriormente.              | `PedidoInvalidoException`    | SIM |
| 5   | Tentativa de pagamento duplicado | `PedidoInvalidoException`    | Pedido [N] inválido: O pedido já foi pago anteriormente.              | `PedidoInvalidoException`    | SIM |

### Caminho feliz

| #   | Cenário                  | Esperado                     | Obtido                                         | OK  |
| --- | ------------------------ | ---------------------------- | ---------------------------------------------- | --- |
| 1   | Cadastrar produto válido | Criado sem exceção           | Objeto `Produto` instanciado com sucesso       | SIM |
| 2   | Criar pedido com 2 itens | Total calculado corretamente | Subtotal e total acumulados via `BigDecimal`   | SIM |
| 3   | Pagar com Pix            | Situação alterada para PAGO  | `SituacaoPedido.PAGO` e estoque reduzido       | SIM |

---

## Parte 4 — Onde cada exceção é tratada

| Exceção                         | Lançada em                                         | Tratada em         | O que o tratamento faz                                                                            |
| ------------------------------- | -------------------------------------------------- | ------------------ | ------------------------------------------------------------------------------------------------- |
| `EstoqueInsuficienteException`  | `Produto.baixarEstoque` / `Pedido.adicionarItem`   | `Aplicacao.main`   | Notifica o estoque disponível, a quantidade solicitada e sugere ajustar o carrinho.               |
| `PagamentoRecusadoException`    | `Pedido.pagar`                                     | `Aplicacao.main`   | Informa o motivo da recusa financeira e mantém o pedido aberto para troca do meio de pagamento.   |
| `PedidoInvalidoException`       | `Pedido.adicionarItem` / `Pedido.pagar`            | `Aplicacao.main`   | Informa o impedimento de regra de negócio (ex.: pedido já pago) e impede a transação.             |
| `ClienteNaoEncontradoException` | `ClienteService` / `Aplicacao`                     | `Aplicacao.main`   | Exibe aviso de busca sem resultados e orienta a conferência do documento.                         |

---

## Parte 5 — Caça aos antipadrões

Busca realizada no projeto inteiro; para cada antipadrão, o número de ocorrências e o que foi feito.

| Antipadrão                                 | Ocorrências | Como foi corrigido                                                                              |
| ------------------------------------------ | ----------- | ----------------------------------------------------------------------------------------------- |
| `catch` vazio                              | 0           | Todos os blocos `catch` efetuam tratamento e notificação adequada do erro.                      |
| `e.printStackTrace()` no código entregue   | 0           | Substituído por mensagens limpas de negócio via `System.out.println`.                           |
| `catch (Exception e)` genérico             | 0           | Substituído por tratamentos específicos de `ECommerceException` e suas filhas.                  |
| Exceção usada para controlar fluxo normal  | 0           | Retornos `boolean` e validações prévias mantidos; exceções restritas a falhas e anomalias.      |
| Mensagem genérica ("erro", "falhou")       | 0           | Todas as mensagens foram enriquecidas com dados do contexto (`nome`, `quantidade`, `numero`).   |
| Recurso aberto e não fechado               | 0           | Garantido o encerramento adequado de streams e objetos.                                         |

---

## Parte 6 — Revisão em pares (Momento 4)

Equipe visitante: **Squad 02 / Revisor Nykolas Guimarães Isler**

| #   | Falha que conseguimos provocar                    | A equipe tinha previsto? | Corrigido?                                   |
| --- | ------------------------------------------------- | ------------------------ | -------------------------------------------- |
| 1   | Adição de itens após o pagamento do pedido        | Sim                      | Sim (lança `PedidoInvalidoException`)        |
| 2   | Processamento de pagamento em pedido sem itens    | Sim                      | Sim (lança `PedidoInvalidoException`)        |
| 3   | Baixa de estoque superior à quantidade disponível | Sim                      | Sim (lança `EstoqueInsuficienteException`)   |
| 4   | Tentativa de pagamento duplicado do mesmo pedido  | Sim                      | Sim (lança `PedidoInvalidoException`)        |

**A falha mais reveladora que recebemos:** *A tentativa de executar duas cobranças consecutivas no mesmo pedido mostrou a necessidade vital de verificar a `SituacaoPedido` antes do processamento do gateway e alterar o estado para `PAGO` imediatamente após a confirmação.*

---

## Parte 7 — Fica para a Aula 10

Cada linha da Parte 3 vira um método de teste. Os já mapeados:

- [x] `deveLancarExcecaoQuandoEstoqueInsuficiente()`
- [x] `deveLancarExcecaoQuandoPrecoNegativo()`
- [x] `deveLancarExcecaoQuandoPedidoSemItens()`
- [x] `deveManterPedidoAbertoQuandoPagamentoRecusado()`
- [x] `deveCalcularTotalCorretamenteComDoisItens()` *(caminho feliz)*
- [x] `deveLancarExcecaoQuandoAdicionarItemEmPedidoPago()`
- [x] `deveLancarExcecaoQuandoPagarPedidoJaPago()`
- [x] `deveLancarExcecaoQuandoNomeProdutoVazio()`
- [x] `deveBaixarEstoqueAposPagamentoBemSucedido()`