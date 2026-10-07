package com.ecommerce.pedidos.naver.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários da Classe ItemPedido")
class ItemPedidoTest {

    @Test
    @DisplayName("Deve calcular subtotal do item corretamente")
    void deveCalcularSubtotalCorretamente() {
        Produto produto = new Produto("PROD-01", "Mouse", new BigDecimal("100.00"), 10);
        ItemPedido item = new ItemPedido(produto, 3);

        assertEquals(new BigDecimal("300.00"), item.calcularSubtotal());
    }

    @Test
    @DisplayName("Deve congelar o preço praticado no momento da criação do item")
    void devePreservarPrecoPraticadoSePrecoDoProdutoMudar() {
        Produto produto = new Produto("PROD-01", "Mouse", new BigDecimal("100.00"), 10);
        ItemPedido item = new ItemPedido(produto, 2);

        produto.setPreco(new BigDecimal("150.00"));

        assertEquals(new BigDecimal("100.00"), item.getPrecoPraticado());
        assertEquals(new BigDecimal("200.00"), item.calcularSubtotal());
    }
}