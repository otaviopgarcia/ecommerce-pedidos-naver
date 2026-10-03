package com.ecommerce.pedidos.naver.excecao;

public class ECommerceException extends Exception {
    public ECommerceException(String mensagem) {
        super(mensagem);
    }

    public ECommerceException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}