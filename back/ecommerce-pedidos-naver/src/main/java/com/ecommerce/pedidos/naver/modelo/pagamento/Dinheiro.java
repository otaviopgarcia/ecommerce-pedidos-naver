package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;

public class Dinheiro implements ProcessadorPagamento {
    private final BigDecimal valorRecebido;

    public Dinheiro(BigDecimal valorRecebido) {
        if (valorRecebido == null || valorRecebido.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor recebido em dinheiro deve ser positivo.");
        }
        this.valorRecebido = valorRecebido;
    }

    @Override
    public boolean processar(BigDecimal valorTotal) {
        if (valorRecebido.compareTo(valorTotal) < 0) {
            System.out.println("Valor recebido em dinheiro é insuficiente.");
            return false;
        }
        BigDecimal troco = valorRecebido.subtract(valorTotal);
        System.out.println("Pagamento em Dinheiro aceito. Troco: R$ " + troco);
        return true;
    }

    @Override
    public String getComprovante() {
        return "RECIBO-DINHEIRO-" + System.currentTimeMillis();
    }

    @Override
    public String getDescricao() {
        return "Pagamento em Dinheiro";
    }
}