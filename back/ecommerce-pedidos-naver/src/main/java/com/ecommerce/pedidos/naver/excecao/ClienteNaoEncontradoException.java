package com.ecommerce.pedidos.naver.excecao;

public class ClienteNaoEncontradoException extends ECommerceException {
    private final String identificador;

    public ClienteNaoEncontradoException(String identificador) {
        super("Cliente não encontrado com o identificador: " + identificador);
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
}