package com.ecommerce.pedidos.naver.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários da Classe Cliente")
class ClienteTest {

    @Test
    @DisplayName("Deve criar cliente válido com sucesso")
    void deveCriarClienteComSucesso() {
        Cliente cliente = new Cliente("Otávio Garcia", "123.456.789-00", "otavio@email.com");
        assertEquals("Otávio Garcia", cliente.getNome());
        assertEquals("123.456.789-00", cliente.getCpf());
        assertEquals("otavio@email.com", cliente.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for vazio ou nulo")
    void deveLancarExcecaoQuandoNomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("", "123.456.789-00", "otavio@email.com"));
        assertThrows(IllegalArgumentException.class, () -> new Cliente(null, "123.456.789-00", "otavio@email.com"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando e-mail for inválido (sem @)")
    void deveLancarExcecaoQuandoEmailInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("Otávio", "123.456.789-00", "otavioemail.com"));
    }
}