package com.ecommerce.pedidos.naver.modelo;

import com.ecommerce.pedidos.naver.excecao.EstoqueInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários da Classe Produto")
class ProdutoTest {

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("PROD-01", "Teclado Mecânico", new BigDecimal("250.00"), 10);
    }

    @Test
    @DisplayName("Deve criar produto com dados válidos")
    void deveCriarProdutoComSucesso() {
        assertAll("Verificação dos atributos do produto", 
            () -> assertEquals("PROD-01", produto.getCodigo()), 
            () -> assertEquals("Teclado Mecânico", produto.getNome()), 
            () -> assertEquals(new BigDecimal("250.00"), produto.getPreco()), 
            () -> assertEquals(10, produto.getQuantidadeEstoque()), 
            () -> assertTrue(produto.isAtivo())
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando código ou nome forem vazios")
    void deveLancarExcecaoQuandoNomeOuCodigoInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Produto("", "Teclado", new BigDecimal("100.00"), 5));
        assertThrows(IllegalArgumentException.class, () -> new Produto("PROD-01", " ", new BigDecimal("100.00"), 5));
    }

    @Test
    @DisplayName("Deve lançar exceção quando preço for nulo ou negativo")
    void deveLancarExcecaoQuandoPrecoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Produto("PROD-01", "Teclado", new BigDecimal("-10.00"), 5));
        assertThrows(IllegalArgumentException.class, () -> new Produto("PROD-01", "Teclado", null, 5));
    }

    @Test
    @DisplayName("Deve dar baixa no estoque com sucesso quando quantidade for válida")
    void deveBaixarEstoqueComSucesso() throws EstoqueInsuficienteException {
        produto.baixarEstoque(3);
        assertEquals(7, produto.getQuantidadeEstoque());
    }

    @Test
    @DisplayName("Deve lançar EstoqueInsuficienteException ao solicitar mais do que o disponível")
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        EstoqueInsuficienteException ex = assertThrows(
            EstoqueInsuficienteException.class, 
            () -> produto.baixarEstoque(15)
        );
        assertEquals(15, ex.getQuantidadeSolicitada());
        assertEquals(produto, ex.getProduto());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao tentar baixar quantidade negativa ou zero")
    void deveLancarExcecaoQuandoQuantidadeBaixaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> produto.baixarEstoque(0));
        assertThrows(IllegalArgumentException.class, () -> produto.baixarEstoque(-2));
    }
}