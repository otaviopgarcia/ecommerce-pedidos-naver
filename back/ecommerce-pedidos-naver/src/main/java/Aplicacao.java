
import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.pedidos.naver.modelo.Cliente;
import com.ecommerce.pedidos.naver.modelo.Pedido;
import com.ecommerce.pedidos.naver.modelo.Produto;
import com.ecommerce.pedidos.naver.modelo.pagamento.Boleto;
import com.ecommerce.pedidos.naver.modelo.pagamento.CartaoCredito;
import com.ecommerce.pedidos.naver.modelo.pagamento.Dinheiro;
import com.ecommerce.pedidos.naver.modelo.pagamento.Pix;
import com.ecommerce.pedidos.naver.modelo.pagamento.ProcessadorPagamento;

public class Aplicacao {
    public static void main(String[] args) {
        System.out.println("=== MÓDULO DE PAGAMENTO POLIMÓRFICO (AULA 08) ===\n");

        Cliente cliente = new Cliente("Otávio Garcia", "123.456.789-00", "otavio@email.com");
        Produto notebook = new Produto("PRD-01", "Notebook Gamer", new BigDecimal("3500.00"), 10);
        Produto mouse = new Produto("PRD-02", "Mouse Sem Fio", new BigDecimal("150.00"), 20);

        // 1. Testando Sobrecarga (adicionarItem)
        Pedido pedido = new Pedido("PED-2026", cliente);
        pedido.adicionarItem(notebook); // Adiciona 1 unidade via sobrecarga
        pedido.adicionarItem(mouse, 2);  // Adiciona 2 unidades via método padrão

        System.out.println("Pedido: " + pedido.getNumero() + " | Cliente: " + pedido.getCliente().getNome());
        System.out.println("Valor Total do Pedido: R$ " + pedido.calcularValorTotal() + "\n");

        // 2. Laço Polimórfico
        List<ProcessadorPagamento> formasPagamento = List.of(
            new Pix(pedido.calcularValorTotal(), "otavio@pix.com"),
            new Boleto(pedido.calcularValorTotal(), "34191.23456 78901.234567 89012.345678 1 90000000380000"),
            new CartaoCredito(pedido.calcularValorTotal(), "4532123456788888", 10),
            new Dinheiro(new BigDecimal("4000.00")) // 4ª forma criada sem alterar o Pedido!
        );

        System.out.println("--- Testando Processamento Polimórfico ---");
        for (ProcessadorPagamento processador : formasPagamento) {
            System.out.println("\nForma: " + processador.getDescricao());
            boolean aprovado = processador.processar(pedido.calcularValorTotal());
            System.out.println("Comprovante: " + processador.getComprovante());
            System.out.println("Resultado: " + (aprovado ? "APROVADO" : "RECUSADO"));
        }

        // 3. Trava de Segurança
        System.out.println("\n--- Teste de Trava de Segurança ---");
        Pedido pedidoVazio = new Pedido("PED-EMPTY", cliente);
        try {
            pedidoVazio.pagar(new Pix(new BigDecimal("100.00"), "chave"));
        } catch (IllegalStateException e) {
            System.out.println("OK (Recusou pagamento de pedido vazio): " + e.getMessage());
        }
    }
}