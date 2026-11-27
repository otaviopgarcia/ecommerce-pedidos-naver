package com.ecommerce.pedidos.naver.servico;

import java.math.BigDecimal;

public interface GatewayPagamento {

    boolean cobrar(BigDecimal valor);

    void emitirComprovante(String identificador);
}