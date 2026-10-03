package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;

public class Pix extends FormaPagamento {
    private final String chave;

    public Pix(BigDecimal valor, String chave) {
        super(valor);
        if (chave == null || chave.isBlank()) {
            throw new IllegalArgumentException("Chave Pix é obrigatória.");
        }
        this.chave = chave;
    }

    public Pix(BigDecimal valor, String chave, String tipoChave) {
        this(valor, chave);
    }

    @Override
    public boolean processar(BigDecimal valor) {
        System.out.println("Processando Pix para chave " + chave + " no valor de R$ " + valor);
        return true;
    }

    @Override
    public String getComprovante() {
        return "PIX-" + System.currentTimeMillis();
    }

    @Override
    public String getDescricao() {
        return "Pagamento via Pix";
    }
}