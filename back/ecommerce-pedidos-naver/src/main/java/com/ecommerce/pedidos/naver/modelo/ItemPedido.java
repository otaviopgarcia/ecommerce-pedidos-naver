package com.ecommerce.pedidos.naver.modelo;

import java.math.BigDecimal;

/**
 * Representa um item selecionado que compõe um carrinho de compras [24].
 * Guarda o preço do produto fixado no momento do checkout [27].
 */
public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private BigDecimal precoPraticado; // Migrado para BigDecimal [10]

    public ItemPedido() {
    }

    public ItemPedido(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto associado ao item é obrigatório.");
        }
        setQuantidade(quantidade); // Delega ao setter para validação [5]
        this.produto = produto;
        this.precoPraticado = produto.getPreco(); // Registra imutavelmente o preço atual [27]
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto associado ao item é obrigatório.");
        }
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade do item deve ser estritamente positiva.");
        }
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoPraticado() {
        return precoPraticado;
    }

    // Nota de negócio: Sem setPrecoPraticado() público para evitar modificações ilegais do preço histórico [6, 27].

    /**
     * Calcula o subtotal financeiro deste item do pedido [27].
     */
    public BigDecimal calcularSubtotal() {
        return this.precoPraticado.multiply(BigDecimal.valueOf(this.quantidade)); // Uso correto de multiply de BigDecimal [28, 29]
    }

    @Override
    public String toString() {
        return String.format("%d x %s - Unitário: R$ %,.2f - Subtotal: R$ %,.2f",
                quantidade, produto.getNome(), precoPraticado, calcularSubtotal());
    }
}