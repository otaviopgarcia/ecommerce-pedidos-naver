package com.ecommerce.pedidos.naver.util;

import java.util.Random;

public class PedidoUtils {

    // Constantes Comerciais Adotadas
    public static final double VALOR_MINIMO_FRETE_GRATIS = 300.00;
    public static final double TAXA_FRETE_PADRAO = 15.00;
    public static final double PERCENTUAL_DESCONTO_AVISTA = 0.05; // 5%
    public static final double LIMITE_DESCONTO_MAXIMO = 50.00;

    // Construtor privado para impedir instanciação da classe utilitária
    private PedidoUtils() {
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada.");
    }

    // 1. Gerar Número do Pedido
    public static String gerarNumeroDoPedido() {
        Random random = new Random();
        int numero = 100000 + random.nextInt(900000); // Gera 6 dígitos aleatórios
        return "PED-" + numero;
    }

    // 2. Calcular Subtotal
    public static double calcularSubtotal(double[] precos, int[] quantidades) {
        if (precos == null || quantidades == null || precos.length != quantidades.length) {
            return 0.0;
        }
        double subtotal = 0.0;
        for (int i = 0; i < precos.length; i++) {
            subtotal += precos[i] * quantidades[i];
        }
        return subtotal;
    }

    // 3. Calcular Frete
    public static double calcularFrete(double subtotal) {
        if (subtotal <= 0.0 || subtotal >= VALOR_MINIMO_FRETE_GRATIS) {
            return 0.0;
        }
        return TAXA_FRETE_PADRAO;
    }

    // 4. Calcular Desconto
    public static double calcularDesconto(double subtotal) {
        double desconto = subtotal * PERCENTUAL_DESCONTO_AVISTA;
        if (desconto > LIMITE_DESCONTO_MAXIMO) {
            return LIMITE_DESCONTO_MAXIMO;
        }
        return desconto;
    }

    // 5. Formatar Linha do Recibo
    public static String formatarLinhaDoRecibo(String nomeProduto, int quantidade, double precoUnitario) {
        double totalItem = precoUnitario * quantidade;
        return String.format("%-20s x%-3d | Un: R$ %7.2f | Total: R$ %7.2f", 
                nomeProduto, quantidade, precoUnitario, totalItem);
    }

    // (Desejável) Método Extra: Montar Recibo Completo com StringBuilder
    public static String montarReciboCompleto(String numPedido, String[] produtos, double[] precos, int[] quantidades) {
        StringBuilder recibo = new StringBuilder();
        
        recibo.append("====================================================\n");
        recibo.append("               RECIBO DE COMPRA - ").append(numPedido).append("\n");
        recibo.append("====================================================\n");

        if (produtos == null || precos == null || quantidades == null || produtos.length == 0) {
            recibo.append("Nenhum item adicionado ao pedido.\n");
        } else {
            for (int i = 0; i < produtos.length; i++) {
                recibo.append(formatarLinhaDoRecibo(produtos[i], quantidades[i], precos[i])).append("\n");
            }
        }

        double subtotal = calcularSubtotal(precos, quantidades);
        double frete = calcularFrete(subtotal);
        double desconto = calcularDesconto(subtotal);
        double totalGeral = subtotal + frete - desconto;

        recibo.append("----------------------------------------------------\n");
        recibo.append(String.format("Subtotal:                                R$ %7.2f\n", subtotal));
        recibo.append(String.format("Frete:                                   R$ %7.2f\n", frete));
        recibo.append(String.format("Desconto Aplicado:                      -R$ %7.2f\n", desconto));
        recibo.append("----------------------------------------------------\n");
        recibo.append(String.format("TOTAL GERAL:                             R$ %7.2f\n", totalGeral));
        recibo.append("====================================================\n");

        return recibo.toString();
    }
}