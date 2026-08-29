package com.ecommerce.pedidos.naver;

import com.ecommerce.pedidos.naver.modelo.Produto;

public class Aplicacao {
    public static void main(String[] args) {
        Produto p = new Produto();
        Produto r = new Produto("COD001",
                "MouseTek",
                "Mouse Gamer",
                300.50,
                50);

    }
}