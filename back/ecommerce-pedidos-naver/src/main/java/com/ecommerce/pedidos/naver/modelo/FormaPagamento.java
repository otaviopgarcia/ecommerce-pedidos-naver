package com.ecommerce.pedidos.naver.modelo;

import java.math.BigDecimal;

/**
 * Representa a abstração das formas de pagamento da loja [5, 37].
 * Servirá como base para as formas de pagamento concretas das próximas entregas [36, 38].
 */
public abstract class FormaPagamento {
    private BigDecimal valor;

    public FormaPagamento(BigDecimal valor) {
        setValor(valor);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser estritamente positivo.");
        }
        this.valor = valor;
    }

    /**
     * Executa o processamento do pagamento. Cada classe filha definirá suas próprias regras [6, 37].
     */
    public abstract boolean processar();
}
