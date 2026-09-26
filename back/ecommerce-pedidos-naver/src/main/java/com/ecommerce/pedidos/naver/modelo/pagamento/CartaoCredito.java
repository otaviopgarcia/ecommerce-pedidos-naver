package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartaoCredito extends FormaPagamento implements ProcessadorPagamento {
    private final String numeroCartao;
    private final int parcelas;

    public CartaoCredito(BigDecimal valor, String numeroCartao, int parcelas) {
        super(valor);
        if (numeroCartao == null || numeroCartao.isBlank()) {
            throw new IllegalArgumentException("Número do cartão é obrigatório.");
        }
        if (parcelas <= 0 || parcelas > 12) {
            throw new IllegalArgumentException("Número de parcelas deve ser entre 1 e 12.");
        }
        this.numeroCartao = numeroCartao;
        this.parcelas = parcelas;
    }

    @Override
    public boolean processar(BigDecimal valor) {
        BigDecimal valorParcela = valor.divide(BigDecimal.valueOf(parcelas), 2, RoundingMode.HALF_UP);
        System.out.println("Processando Cartão em " + parcelas + "x de R$ " + valorParcela);
        return true;
    }

    @Override
    public String getComprovante() {
        return "CARD-" + System.currentTimeMillis();
    }

    @Override
    public String getDescricao() {
        return "Pagamento via Cartão de Crédito (" + parcelas + "x)";
    }
}