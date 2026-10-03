import java.math.BigDecimal;

import com.ecommerce.pedidos.naver.excecao.ECommerceException;
import com.ecommerce.pedidos.naver.excecao.EstoqueInsuficienteException;
import com.ecommerce.pedidos.naver.excecao.PedidoInvalidoException;
import com.ecommerce.pedidos.naver.modelo.Cliente;
import com.ecommerce.pedidos.naver.modelo.Pedido;
import com.ecommerce.pedidos.naver.modelo.Produto;
import com.ecommerce.pedidos.naver.modelo.pagamento.Pix;
import com.ecommerce.pedidos.naver.modelo.pagamento.ProcessadorPagamento;

public class Aplicacao {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE PEDIDOS (E-COMMERCE NAVER) — AULA 09 ===");

        Cliente cliente = new Cliente("Otávio Garcia", "123.456.789-00", "otavio@email.com");
        Produto p1 = new Produto("PROD-01", "Teclado Mecânico", new BigDecimal("250.00"), 3);

        System.out.println("\n--- CENÁRIO 1: Tentativa de adicionar produto com estoque insuficiente ---");
        Pedido pedido1 = new Pedido("PED-001", cliente);
        try {
            pedido1.adicionarItem(p1, 5);
        } catch (EstoqueInsuficienteException e) {
            System.out.println(" Falha tratada: " + e.getMessage());
            System.out.println(" Produto: " + e.getProduto().getNome() + " | Solicitado: " + e.getQuantidadeSolicitada());
        } catch (ECommerceException e) {
            System.out.println(" Erro de negócio: " + e.getMessage());
        }

        System.out.println("\n--- CENÁRIO 2: Tentativa de pagar pedido sem itens ---");
        Pedido pedidoVazio = new Pedido("PED-002", cliente);
        try {
            ProcessadorPagamento pix = new Pix(new BigDecimal("100.00"), "chave@pix.com");
            pedidoVazio.pagar(pix);
        } catch (PedidoInvalidoException e) {
            System.out.println(" Falha tratada: " + e.getMessage());
        } catch (ECommerceException e) {
            System.out.println(" Erro de negócio: " + e.getMessage());
        }

        System.out.println("\n--- CENÁRIO 3: Caminho feliz e tentativa de pagar 2 vezes ---");
        try {
            Pedido pedidoOk = new Pedido("PED-003", cliente);
            pedidoOk.adicionarItem(p1, 2);
            ProcessadorPagamento pix = new Pix(pedidoOk.calcularValorTotal(), "otavio@pix.com");
            pedidoOk.pagar(pix);

            System.out.println("\nTentando pagar o mesmo pedido uma 2ª vez:");
            pedidoOk.pagar(pix);
        } catch (PedidoInvalidoException e) {
            System.out.println(" Falha tratada: " + e.getMessage());
        } catch (ECommerceException e) {
            System.out.println(" Erro de negócio: " + e.getMessage());
        }

        System.out.println("\n=== EXECUÇÃO CONCLUÍDA COM SUCESSO E SEM CRASHES ===");
    }
}