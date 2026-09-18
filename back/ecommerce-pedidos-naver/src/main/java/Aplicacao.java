package com.ecommerce.pedidos.naver;

import com.ecommerce.pedidos.naver.modelo.*;
import com.ecommerce.pedidos.naver.modelo.pagamento.Pix;
import java.math.BigDecimal;

public class Aplicacao {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Otávio Garcia", "123.456.789-00", "otavio@email.com");
        Produto teclado = new Produto("TEC-01", "Teclado Mecânico", new BigDecimal("150.00"), 5);

        // 1. Validar recusa de pedido sem cliente
        try {
            new Pedido("PED-100", null);
        } catch (IllegalArgumentException e) {
            System.out.println("OK: " + e.getMessage());
        }

        Pedido pedido = new Pedido("PED-101", cliente);

        // 2. Validar recusa de pagamento sem itens
        try {
            pedido.pagarCom(new Pix(new BigDecimal("150.00"), "chave-pix"));
        } catch (IllegalStateException e) {
            System.out.println("OK: " + e.getMessage());
        }

        // 3. Adicionar itens e testar unificação de produto repetido
        pedido.adicionarItem(teclado, 2);
        pedido.adicionarItem(teclado, 1);
        System.out.println("Qtd unificada: " + pedido.getItens().get(0).getQuantidade()); // 3

        // 4. Testar proteção de coleção
        try {
            pedido.getItens().clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("OK: Coleção protegida contra alterações externas.");
        }
    }
}