package com.ecommerce.pedidos.naver.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Agrupa o cliente, seus itens selecionados, a data e o status do ciclo de vida [24].
 */
public class Pedido {
    private String numero;
    private Cliente cliente;
    private String data;
    private SituacaoPedido situacao; // Requisito desejável: agora usando Enum [7]
    private List<ItemPedido> itens = new ArrayList<>(); // Lista encapsulada [6, 31]

    public Pedido() {
    }

    public Pedido(String numero, Cliente cliente, String data) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("O número de controle do pedido é obrigatório.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente que realizou a compra é obrigatório.");
        }
        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException("A data de abertura do pedido é obrigatória.");
        }
        this.numero = numero.trim();
        this.cliente = cliente;
        this.data = data.trim();
        this.situacao = SituacaoPedido.ABERTO; // Todo pedido nasce em aberto [33]
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getData() {
        return data;
    }

    public SituacaoPedido getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoPedido situacao) {
        if (situacao == null) {
            throw new IllegalArgumentException("A situação do pedido não pode ser nula.");
        }
        this.situacao = situacao;
    }

    /**
     * BLINDAGEM DE COLEÇÃO (Retorna cópia imutável de leitura) [30, 31]
     * Impede de forma absoluta manipulações indesejadas por fora dos métodos de negócio [10].
     */
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    /**
     * Adiciona um item ao carrinho do pedido.
     * Realiza automaticamente a baixa do estoque do produto [34, 35].
     */
    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("Não é possível adicionar um item nulo ao pedido.");
        }
        // Se houver saldo para atender a compra, procede o registro
        if (item.getProduto().temEstoqueDisponivel(item.getQuantidade())) {
            this.itens.add(item);
            item.getProduto().baixarEstoque(item.getQuantidade()); // Deduz o saldo físico do produto [34]
        } else {
            throw new IllegalStateException("Pedido bloqueado: Estoque de '" + 
                    item.getProduto().getNome() + "' é insuficiente.");
        }
    }

    /**
     * Calcula o valor financeiro acumulado de todos os itens do pedido [34, 35].
     */
    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : this.itens) {
            total = total.add(item.calcularSubtotal()); // Uso correto de somas com BigDecimal [18]
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================").append(System.lineSeparator());
        sb.append("PEDIDO: ").append(numero).append(" | Data: ").append(data).append(System.lineSeparator());
        sb.append("Cliente: ").append(cliente.getIdentificacao()).append(System.lineSeparator());
        sb.append("Situação: ").append(situacao).append(System.lineSeparator());
        sb.append("------------------------------------------").append(System.lineSeparator());
        for (ItemPedido item : itens) {
            sb.append(" - ").append(item).append(System.lineSeparator());
        }
        sb.append("------------------------------------------").append(System.lineSeparator());
        sb.append(String.format("VALOR TOTAL DO PEDIDO: R$ %,.2f", calcularValorTotal())).append(System.lineSeparator());
        sb.append("==========================================");
        return sb.toString();
    }
}