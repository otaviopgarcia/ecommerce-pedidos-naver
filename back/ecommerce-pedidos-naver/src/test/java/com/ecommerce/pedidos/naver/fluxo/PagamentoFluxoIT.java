package com.ecommerce.pedidos.naver.fluxo;

import com.ecommerce.pedidos.naver.excecao.EstoqueInsuficienteException;
import com.ecommerce.pedidos.naver.excecao.PagamentoRecusadoException;
import com.ecommerce.pedidos.naver.excecao.PedidoInvalidoException;
import com.ecommerce.pedidos.naver.modelo.Cliente;
import com.ecommerce.pedidos.naver.modelo.Pedido;
import com.ecommerce.pedidos.naver.modelo.Produto;
import com.ecommerce.pedidos.naver.modelo.SituacaoPedido;
import com.ecommerce.pedidos.naver.servico.GatewayPagamento;
import com.ecommerce.pedidos.naver.servico.ServicoPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Integração - Gateway de Pagamento com Mockito")
class PagamentoFluxoIT {

    @Mock
    private GatewayPagamento gateway;

    @InjectMocks
    private ServicoPedido servico;

    private Cliente cliente;
    private Produto monitor;

    @BeforeEach
    void setup() {
        cliente = new Cliente("Carlos", "111.222.333-44", "carlos@email.com");
        monitor = new Produto("PROD-02", "Monitor 24", new BigDecimal("1000.00"), 10);
    }

    private Pedido criarPedidoCenario() throws EstoqueInsuficienteException, PedidoInvalidoException {
        Pedido pedido = new Pedido("PED-003", cliente);
        pedido.adicionarItem(monitor, 2);
        return pedido;
    }

    @Test
    @DisplayName("Deve marcar pedido como pago e cobrar valor exato quando gateway aprova")
    void deveMarcarPedidoComoPagoQuandoGatewayAprova() throws Exception {
        // Arrange
        when(gateway.cobrar(any(BigDecimal.class))).thenReturn(true);
        Pedido pedido = criarPedidoCenario();

        // Act
        Pedido pedidoFinalizado = servico.finalizar(pedido);

        // Assert
        assertEquals(SituacaoPedido.PAGO, pedidoFinalizado.getSituacao());
        assertEquals(8, monitor.getQuantidadeEstoque());

        verify(gateway).cobrar(new BigDecimal("2000.00"));
        verify(gateway).emitirComprovante(pedido.getNumero());
    }

    @Test
    @DisplayName("Deve manter pedido aberto, estoque intacto e não emitir comprovante quando gateway recusa")
    void deveManterPedidoAbertoQuandoGatewayRecusa() throws EstoqueInsuficienteException, PedidoInvalidoException {
        // Arrange
        when(gateway.cobrar(any())).thenReturn(false);
        Pedido pedido = criarPedidoCenario();

        // Act & Assert
        assertThrows(PagamentoRecusadoException.class, () -> servico.finalizar(pedido));

        assertAll(
            () -> assertEquals(SituacaoPedido.CRIADO, pedido.getSituacao()),
            () -> assertEquals(10, monitor.getQuantidadeEstoque()),
            () -> verify(gateway, never()).emitirComprovante(any())
        );
    }
}