package com.ecommerce.pedidos.naver.excecao;

public class PagamentoRecusadoException extends ECommerceException {
    private final String motivo;

    public PagamentoRecusadoException(String formaPagamento, String motivo) {
        super("Pagamento por " + formaPagamento + " recusado: " + motivo);
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}