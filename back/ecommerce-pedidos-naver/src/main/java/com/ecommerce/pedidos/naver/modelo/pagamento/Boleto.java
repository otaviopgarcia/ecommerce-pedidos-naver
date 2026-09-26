package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;

public class Boleto extends FormaPagamento implements ProcessadorPagamento {
    private final String codigoBarras;

    public Boleto(BigDecimal valor, String codigoBarras) {
        super(valor);
        if (codigoBarras == null || codigoBarras.isBlank()) {
            throw new IllegalArgumentException("Código de barras é obrigatório.");
        }
        this.codigoBarras = codigoBarras;
    }

    @Override
    public boolean processar(BigDecimal valor) {
        System.out.println("Gerando boleto com código " + codigoBarras + " no valor de R$ " + valor);
        return true;
    }

    @Override
    public String getComprovante() {
        return "BOL-" + System.currentTimeMillis();
    }

    @Override
    public String getDescricao() {
        return "Pagamento via Boleto Bancário";
    }
}