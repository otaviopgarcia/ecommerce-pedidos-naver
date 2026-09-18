package com.ecommerce.pedidos.naver.modelo;

import com.ecommerce.pedidos.naver.modelo.pagamento.FormaPagamento;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private final String numero;
    private final Cliente cliente; // Associação 1 (Obrigatória)
    private final List<ItemPedido> itens = new ArrayList<>(); // Composição
    private FormaPagamento formaPagamento; // Associação 0..1 (Opcional)

    public Pedido(String numero, Cliente cliente) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Número do pedido é obrigatório.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Pedido exige um cliente válido."); // Validação de multiplicidade (1)
        }
        this.numero = numero;
        this.cliente = cliente;
    }

    // Composição: o próprio Pedido instancia o ItemPedido
    public void adicionarItem(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (!produto.temEstoqueDisponivel(quantidade)) {
            throw new IllegalStateException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        // Regra do produto repetido: soma a quantidade no item existente
        for (ItemPedido item : itens) {
            if (item.getProduto().getCodigo().equals(produto.getCodigo())) {
                item.adicionarQuantidade(quantidade);
                return;
            }
        }

        itens.add(new ItemPedido(produto, quantidade, produto.getPreco()));
    }

    // Proteção de coleção
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            total = total.add(item.calcularSubtotal());
        }
        return total;
    }

    public void pagarCom(FormaPagamento formaPagamento) {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido sem itens (1..*) não pode ser pago."); // Validação multiplicidade (1..*)
        }
        if (formaPagamento == null) {
            throw new IllegalArgumentException("Forma de pagamento não pode ser nula.");
        }
        this.formaPagamento = formaPagamento;
        formaPagamento.processar();
    }

    public String getNumero() { 
        return numero; 
    }

    public Cliente getCliente() { 
        return cliente; 
    }

    public FormaPagamento getFormaPagamento() { 
        return formaPagamento; 
    }
}