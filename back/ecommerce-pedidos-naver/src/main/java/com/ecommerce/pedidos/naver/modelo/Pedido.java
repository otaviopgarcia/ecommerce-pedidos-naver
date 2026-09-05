package com.ecommerce.pedidos.naver.modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String numero;
    private Cliente cliente;
    private String data;
    private String situacao; // Ex: "Aberto", "Pago", "Enviado"
    private List<ItemPedido> itens = new ArrayList<>(); // Lista dinâmica iniciada vazia

    public Pedido() {}

    public Pedido(String numero, Cliente cliente, String data) {
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.situacao = "ABERTO"; // Todo pedido nasce aberto
    }

    // Getters e Setters (itens entram apenas pelo método de negócio abaixo)
    public String getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public String getData() { return data; }
    
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public List<ItemPedido> getItens() { return itens; }

    // Método de negócio: Adiciona item ao pedido e dá baixa no estoque do produto
    public void adicionarItem(ItemPedido item) {
        if (item.getProduto().temEstoqueDisponivel(item.getQuantidade())) {
            this.itens.add(item);
            item.getProduto().baixarEstoque(item.getQuantidade()); // Dá baixa automática!
        } else {
            System.out.println("ERRO: Estoque insuficiente para o produto: " + item.getProduto().getNome());
        }
    }

    // Método de negócio: Soma o subtotal de todos os itens do pedido
    public double calcularValorTotal() {
        double total = 0.0;
        for (ItemPedido item : this.itens) {
            total += item.calcularSubtotal();
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
        sb.append(String.format("VALOR TOTAL DO PEDIDO: R$ %.2f", calcularValorTotal())).append(System.lineSeparator());
        sb.append("==========================================");
        return sb.toString();
    }
}