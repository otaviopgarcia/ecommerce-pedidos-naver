package com.ecommerce.pedidos.naver.fluxo;

import com.ecommerce.pedidos.naver.modelo.Cliente;
import com.ecommerce.pedidos.naver.modelo.Pedido;
import com.ecommerce.pedidos.naver.modelo.Produto;
import com.ecommerce.pedidos.naver.modelo.SituacaoPedido;
import com.ecommerce.pedidos.naver.modelo.pagamento.Pix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Integração - Fluxo de Pedidos (Objetos Reais)")
class PedidoFluxoIT {

    private Cliente cliente;
    private Produto notebook;

    @BeforeEach
    void prepararCenario() {
        cliente = new Cliente("Ana", "123.456.789-00", "ana@email.com");
        notebook = new Produto("PROD-01", "Notebook", new BigDecimal("3000.00"), 5);
    }

    @Test
    @DisplayName("Deve criar, somar e pagar pedido com sucesso")
    void deveExecutarFluxoCompletoDePedido() throws Exception {
        // Arrange
        Pedido pedido = new Pedido("PED-001", cliente);
        pedido.adicionarItem(notebook, 2);

        // Act
        boolean pago = pedido.pagar(new Pix(new BigDecimal("6000.00"), "ana@pix.com"));

        // Assert
        assertAll(
            () -> assertTrue(pago),
            () -> assertEquals(0, new BigDecimal("6000.00").compareTo(pedido.calcularValorTotal())),
            () -> assertEquals(SituacaoPedido.PAGO, pedido.getSituacao()),
            () -> assertEquals(1, pedido.getItens().size())
        );
    }

    @Test
    @DisplayName("Deve recusar pagamento de pedido sem itens mantendo estado íntegro")
    void deveRecusarPagamentoDePedidoSemItens() {
        Pedido pedido = new Pedido("PED-002", cliente);

        assertThrows(IllegalStateException.class, () ->
            pedido.pagar(new Pix(new BigDecimal("100.00"), "ana@pix.com"))
        );

        assertEquals(SituacaoPedido.CRIADO, pedido.getSituacao());
    }
}