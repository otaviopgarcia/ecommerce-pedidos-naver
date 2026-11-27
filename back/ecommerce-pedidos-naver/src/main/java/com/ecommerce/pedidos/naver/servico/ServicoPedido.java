package com.ecommerce.pedidos.naver.servico;

import com.ecommerce.pedidos.naver.excecao.PagamentoRecusadoException;
import com.ecommerce.pedidos.naver.modelo.ItemPedido;
import com.ecommerce.pedidos.naver.modelo.Pedido;
import com.ecommerce.pedidos.naver.modelo.SituacaoPedido;

import java.math.BigDecimal;

public class ServicoPedido {

    private final GatewayPagamento gateway;

    public ServicoPedido(GatewayPagamento gateway) {
        this.gateway = gateway;
    }

    public Pedido finalizar(Pedido pedido) throws Exception {
        if (pedido == null || pedido.getItens().isEmpty()) {
            throw new IllegalStateException("Pedido inválido para finalização");
        }

        BigDecimal valorTotal = pedido.calcularValorTotal();
        boolean cobrado = gateway.cobrar(valorTotal);

        if (!cobrado) {
            throw new PagamentoRecusadoException("Gateway", "Transação recusada pela operadora");
        }

        pedido.setSituacao(SituacaoPedido.PAGO);

        for (ItemPedido item : pedido.getItens()) {
            int novaQtd = item.getProduto().getQuantidadeEstoque() - item.getQuantidade();
            item.getProduto().setQuantidadeEstoque(novaQtd);
        }

        gateway.emitirComprovante(pedido.getNumero());
        return pedido;
    }
}