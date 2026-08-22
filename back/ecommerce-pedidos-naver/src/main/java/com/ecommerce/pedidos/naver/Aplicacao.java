package com.ecommerce.pedidos.naver;

import com.ecommerce.pedidos.naver.util.PedidoUtils;

public class Aplicacao {
    public static void main(String[] args) {
        System.out.println("Iniciando Simulações de Pedidos (Casos de Teste)\n");

        // --- CENÁRIO 1: Compra Comum (Abaixo de R$ 300, frete cobrado) ---
        System.out.println("--- Cenário 1: Compra Comum ---");
        String[] prod1 = {"Teclado Mecânico", "Mouse Pad Gamer"};
        double[] prec1 = {120.00, 45.00};
        int[] qtd1 = {1, 2}; // Subtotal: 210.00
        
        String pedido1 = PedidoUtils.gerarNumeroDoPedido();
        System.out.println(PedidoUtils.montarReciboCompleto(pedido1, prod1, prec1, qtd1));

        // --- CENÁRIO 2: Compra com Frete Grátis (Acima de R$ 300,00) ---
        System.out.println("\n--- Cenário 2: Compra Acima de R$ 300 (Frete Grátis) ---");
        String[] prod2 = {"Monitor UltraWide", "Cabo HDMI"};
        double[] prec2 = {1150.00, 35.00};
        int[] qtd2 = {1, 1}; // Subtotal: 1185.00 (Desconto bate no teto de R$ 50)
        
        String pedido2 = PedidoUtils.gerarNumeroDoPedido();
        System.out.println(PedidoUtils.montarReciboCompleto(pedido2, prod2, prec2, qtd2));

        // --- CENÁRIO 3: Caso Limite (Valor muito baixo) ---
        System.out.println("\n--- Cenário 3: Caso Limite (Valor muito baixo) ---");
        String[] prod3 = {"Caneta Esferográfica"};
        double[] prec3 = {2.50};
        int[] qtd3 = {1}; // Subtotal: 2.50
        
        String pedido3 = PedidoUtils.gerarNumeroDoPedido();
        System.out.println(PedidoUtils.montarReciboCompleto(pedido3, prod3, prec3, qtd3));

        // --- CENÁRIO 4: Caso Limite Extremo (Vetores vazios) ---
        System.out.println("\n--- Cenário 4: Caso Limite (Carrinho Vazio) ---");
        String[] prod4 = {};
        double[] prec4 = {};
        int[] qtd4 = {};
        
        String pedido4 = PedidoUtils.gerarNumeroDoPedido();
        System.out.println(PedidoUtils.montarReciboCompleto(pedido4, prod4, prec4, qtd4));
    }
}