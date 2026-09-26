package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;

public interface ProcessadorPagamento {
    boolean processar(BigDecimal valor);
    String getComprovante();
    String getDescricao();
}