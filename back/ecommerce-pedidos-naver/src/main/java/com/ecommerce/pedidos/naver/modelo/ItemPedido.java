package com.ecommerce.pedidos.naver.modelo;

public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoPraticado; // Registra o preço do momento histórico da compra

    public ItemPedido() {}

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoPraticado = produto.getPreco(); // Copia automaticamente o preço atual do produto
    }

    // Getters e Setters (Preço praticado não deve ter setter para evitar fraude)
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoPraticado() { return precoPraticado; }

    // Método de negócio: calcula o subtotal do item (preço x quantidade)
    public double calcularSubtotal() {
        return this.precoPraticado * this.quantidade;
    }

    @Override
    public String toString() {
        return String.format("%d x %s - Unitário: R$ %.2f - Subtotal: R$ %.2f", 
                quantidade, produto.getNome(), precoPraticado, calcularSubtotal());
    }
}