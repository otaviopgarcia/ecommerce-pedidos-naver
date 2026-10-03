package com.ecommerce.pedidos.naver.modelo;

import com.ecommerce.pedidos.naver.excecao.EstoqueInsuficienteException;
import com.ecommerce.pedidos.naver.excecao.PagamentoRecusadoException;
import com.ecommerce.pedidos.naver.excecao.PedidoInvalidoException;
import com.ecommerce.pedidos.naver.modelo.pagamento.ProcessadorPagamento;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private String numero;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private LocalDateTime dataCriacao;
    private SituacaoPedido situacao;

    public Pedido(String numero, Cliente cliente) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("Numero do pedido nao pode ser vazio.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente do pedido nao pode ser nulo.");
        }
        this.numero = numero;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.situacao = SituacaoPedido.CRIADO;
    }

    public void adicionarItem(Produto produto) throws EstoqueInsuficienteException, PedidoInvalidoException {
        adicionarItem(produto, 1);
    }

    public void adicionarItem(Produto produto, int quantidade) throws EstoqueInsuficienteException, PedidoInvalidoException {
        if (this.situacao == SituacaoPedido.PAGO) {
            throw new PedidoInvalidoException(numero, "Não é possível adicionar itens a um pedido que já está PAGO.");
        }
        if (produto == null) {
            throw new IllegalArgumentException("Produto nao pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        if (quantidade > produto.getQuantidadeEstoque()) {
            throw new EstoqueInsuficienteException(produto, quantidade);
        }

        for (ItemPedido item : itens) {
            if (item.getProduto().getCodigo().equals(produto.getCodigo())) {
                int novaQuantidade = item.getQuantidade() + quantidade;
                if (novaQuantidade > produto.getQuantidadeEstoque()) {
                    throw new EstoqueInsuficienteException(produto, novaQuantidade);
                }
                item.adicionarQuantidade(quantidade);
                return;
            }
        }

        this.itens.add(new ItemPedido(produto, quantidade));
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            total = total.add(item.calcularSubtotal());
        }
        return total;
    }

    public boolean pagar(ProcessadorPagamento processador) throws PagamentoRecusadoException, PedidoInvalidoException, EstoqueInsuficienteException {
        if (processador == null) {
            throw new IllegalArgumentException("Processador de pagamento é obrigatório.");
        }
        if (this.situacao == SituacaoPedido.PAGO) {
            throw new PedidoInvalidoException(numero, "O pedido já foi pago anteriormente.");
        }
        if (itens.isEmpty()) {
            throw new PedidoInvalidoException(numero, "Não é possível pagar um pedido sem itens.");
        }

        boolean comSucesso = processador.processar(calcularValorTotal());
        if (!comSucesso) {
            throw new PagamentoRecusadoException(processador.getDescricao(), "Transação recusada pela operadora de pagamento.");
        }

        for (ItemPedido item : itens) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }

        this.situacao = SituacaoPedido.PAGO;
        System.out.println("Pagamento realizado com sucesso para o Pedido " + numero);
        System.out.println("Descrição: " + processador.getDescricao());
        System.out.println("Comprovante: " + processador.getComprovante());
        return true;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public SituacaoPedido getSituacao() {
        return situacao;
    }
}