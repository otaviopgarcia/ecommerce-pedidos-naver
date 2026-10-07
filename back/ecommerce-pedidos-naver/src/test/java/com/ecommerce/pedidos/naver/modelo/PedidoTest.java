package com.ecommerce.pedidos.naver.modelo;

import com.ecommerce.pedidos.naver.excecao.EstoqueInsuficienteException;
import com.ecommerce.pedidos.naver.excecao.PedidoInvalidoException;
import com.ecommerce.pedidos.naver.modelo.pagamento.Pix;
import com.ecommerce.pedidos.naver.modelo.pagamento.ProcessadorPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários da Classe Pedido")
class PedidoTest {

    private Cliente cliente;
    private Produto produto1;
    private Produto produto2;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Otávio Garcia", "123.456.789-00", "otavio@email.com");
        produto1 = new Produto("PROD-01", "Teclado", new BigDecimal("200.00"), 5);
        produto2 = new Produto("PROD-02", "Mouse", new BigDecimal("100.00"), 3);
        pedido = new Pedido("PED-001", cliente);
    }

    @Test
    @DisplayName("Deve iniciar pedido no estado CRIADO e sem itens")
    void deveIniciarPedidoCorretamente() {
        assertEquals(SituacaoPedido.CRIADO, pedido.getSituacao());
        assertTrue(pedido.getItens().isEmpty());
        assertEquals(BigDecimal.ZERO, pedido.calcularValorTotal());
    }

    @Test
    @DisplayName("Deve adicionar itens e calcular total do pedido corretamente")
    void deveAdicionarItensECalcularTotal() throws EstoqueInsuficienteException, PedidoInvalidoException {
        pedido.adicionarItem(produto1, 2);
        pedido.adicionarItem(produto2, 1);
        
        assertEquals(2, pedido.getItens().size());
        assertEquals(new BigDecimal("500.00"), pedido.calcularValorTotal());
    }

    @Test
    @DisplayName("Deve somar quantidade ao adicionar produto repetido")
    void deveSomarQuantidadeAoAdicionarProdutoRepetido() throws EstoqueInsuficienteException, PedidoInvalidoException {
        pedido.adicionarItem(produto1, 2);
        pedido.adicionarItem(produto1, 1);
        
        assertEquals(1, pedido.getItens().size());
        assertEquals(3, pedido.getItens().get(0).getQuantidade());
        assertEquals(new BigDecimal("600.00"), pedido.calcularValorTotal());
    }

    @Test
    @DisplayName("Deve lançar EstoqueInsuficienteException ao adicionar quantidade superior ao estoque")
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        assertThrows(EstoqueInsuficienteException.class, () -> pedido.adicionarItem(produto1, 10));
    }

    @Test
    @DisplayName("Deve lançar PedidoInvalidoException ao tentar pagar pedido sem itens")
    void deveLancarExcecaoAoPagarPedidoVazio() {
        ProcessadorPagamento pix = new Pix(new BigDecimal("100.00"), "chave@pix.com", "EMAIL");
        
        assertThrows(PedidoInvalidoException.class, () -> pedido.pagar(pix));
    }

    @Test
    @DisplayName("Deve realizar pagamento com sucesso e dar baixa no estoque")
    void devePagarPedidoComSucesso() throws Exception {
        pedido.adicionarItem(produto1, 2);
        ProcessadorPagamento pix = new Pix(pedido.calcularValorTotal(), "chave@pix.com", "EMAIL");
        
        boolean resultado = pedido.pagar(pix);
        
        assertTrue(resultado);
        assertEquals(SituacaoPedido.PAGO, pedido.getSituacao());
        assertEquals(3, produto1.getQuantidadeEstoque());
    }

    @Test
    @DisplayName("Deve lançar PedidoInvalidoException ao tentar pagar pedido já pago")
    void deveLancarExcecaoAoPagarPedidoJaPago() throws Exception {
        pedido.adicionarItem(produto1, 1);
        ProcessadorPagamento pix = new Pix(pedido.calcularValorTotal(), "chave@pix.com", "EMAIL");
        pedido.pagar(pix);
        
        assertThrows(PedidoInvalidoException.class, () -> pedido.pagar(pix));
    }

    @Test
    @DisplayName("Deve lançar PedidoInvalidoException ao tentar adicionar item em pedido PAGO")
    void deveLancarExcecaoAoAdicionarItemEmPedidoPago() throws Exception {
        pedido.adicionarItem(produto1, 1);
        ProcessadorPagamento pix = new Pix(pedido.calcularValorTotal(), "chave@pix.com", "EMAIL");
        pedido.pagar(pix);
        
        assertThrows(PedidoInvalidoException.class, () -> pedido.adicionarItem(produto2, 1));
    }
}