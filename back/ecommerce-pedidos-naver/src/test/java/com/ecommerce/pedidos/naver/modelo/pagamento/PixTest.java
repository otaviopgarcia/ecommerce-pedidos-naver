package com.ecommerce.pedidos.naver.modelo.pagamento;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários da Forma de Pagamento Pix")
class PixTest {

    @Test
    @DisplayName("Deve criar Pix e processar pagamento com sucesso")
    void deveProcessarPagamentoPixComSucesso() {
        Pix pix = new Pix(new BigDecimal("150.00"), "otavio@pix.com", "EMAIL");

        assertTrue(pix.processar(new BigDecimal("150.00")));
        assertNotNull(pix.getComprovante());
        assertTrue(pix.getDescricao().contains("Pix"));
    }

    @Test
    @DisplayName("Deve lançar exceção para chave ou tipo de chave inválidos")
    void deveLancarExcecaoParaChaveInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Pix(new BigDecimal("100.00"), "", "EMAIL"));
        assertThrows(IllegalArgumentException.class, () -> new Pix(new BigDecimal("100.00"), "chave", null));
    }
}