package com.ecommerce.pedidos.naver.excecao;

import com.ecommerce.pedidos.naver.modelo.Produto;

public class EstoqueInsuficienteException extends ECommerceException {
    private final Produto produto;
    private final int quantidadeSolicitada;

    public EstoqueInsuficienteException(Produto produto, int quantidadeSolicitada) {
        super("Estoque insuficiente de " + produto.getNome() + ": disponível " + produto.getQuantidadeEstoque()
                + ", solicitado " + quantidadeSolicitada);
        this.produto = produto;
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
}