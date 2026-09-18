package com.ecommerce.pedidos.naver.modelo;

import java.math.BigDecimal;

public class ItemPedido {

    private final Produto produto;
    private int quantidade;
    private final BigDecimal precoPraticado; // Preço congelado no momento da compra

    public ItemPedido(Produto produto, int quantidade, BigDecimal precoPraticado) {

        if (produto == null) {
            throw new IllegalArgumentException(
                "Produto é obrigatório para o item."
            );
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "Quantidade deve ser maior que zero."
            );
        }

        if (precoPraticado == null
                || precoPraticado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                "Preço praticado é inválido."
            );
        }

        this.produto = produto;
        this.quantidade = quantidade;
        this.precoPraticado = precoPraticado;
    }

    public BigDecimal calcularSubtotal() {
        return precoPraticado.multiply(
            BigDecimal.valueOf(quantidade)
        );
    }

    public void adicionarQuantidade(int qtd) {

        if (qtd <= 0) {
            throw new IllegalArgumentException(
                "Quantidade a adicionar deve ser positiva."
            );
        }

        this.quantidade += qtd;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoPraticado() {
        return precoPraticado;
    }
}