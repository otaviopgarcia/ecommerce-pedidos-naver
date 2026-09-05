
import com.ecommerce.pedidos.naver.modelo.*;
import java.math.BigDecimal;

public class Aplicacao {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   VALIDAÇÃO DE ENCAPSULAMENTO - SQUAD    ");
        System.out.println("==========================================\n");

        // ----------------------------------------------------
        // 1. CAMINHO TRISTE: Testando as barreiras de segurança [40]
        // ----------------------------------------------------
        System.out.println(">>> 1. TESTANDO AS BARREIRAS DE SEGURANÇA (CAMINHO TRISTE):");

        // Tentativa A: Produto com preço negativo
        try {
            Produto pInvalido = new Produto("TEC-1", "Teclado", "RGB", new BigDecimal("-150.00"), 10);
            System.out.println("ERRO: O sistema permitiu criar produto com preço negativo!");
        } catch (IllegalArgumentException e) {
            System.out.println("DEFESA OK: Recusou preço negativo -> " + e.getMessage());
        }

        // Tentativa B: Alterar preço de produto existente para negativo
        Produto teclado = new Produto("TEC-1", "Teclado Mecânico", "Teclado Gamer Switch Blue", new BigDecimal("150.00"), 10);
        try {
            teclado.setPreco(new BigDecimal("-1.00"));
            System.out.println("ERRO: O sistema aceitou alterar preço para negativo!");
        } catch (IllegalArgumentException e) {
            System.out.println("DEFESA OK: Recusou alteração para preço negativo -> " + e.getMessage());
        }

        // Tentativa C: Estourar o estoque físico (baixarEstoque maior que o saldo)
        try {
            teclado.baixarEstoque(15); // Ele só possui 10 no saldo físico
            System.out.println("ERRO: O sistema aceitou baixar mais estoque do que o disponível!");
        } catch (IllegalStateException e) {
            System.out.println("DEFESA OK: Impediu estouro de estoque físico -> " + e.getMessage());
        }

        // Tentativa D: Violar a coleção de itens por fora do Pedido
        Cliente cliente = new Cliente("Ana Souza", "123.456.789-00", "ana@email.com", "11 99999-9999", "Rua das Flores, 123");
        Pedido pedido = new Pedido("PED-2026-00001", cliente, "04/09/2026");
        try {
            pedido.getItens().clear(); // Se der certo, viola a integridade da lista
            System.out.println("ERRO: A coleção foi burlada por fora do pedido!");
        } catch (UnsupportedOperationException e) {
            System.out.println("DEFESA OK: Coleção protegida e imutável externamente!");
        }

        // ----------------------------------------------------
        // 2. CAMINHO FELIZ: Fluxo comercial com sucesso [40]
        // ----------------------------------------------------
        System.out.println("\n>>> 2. FLUXO COMERCIAL NORMAL (CAMINHO FELIZ):");

        Produto monitor = new Produto("MON-2", "Monitor Ultra", "Monitor IPS 29 Polegadas", new BigDecimal("899.90"), 5);

        System.out.println("\n--- Catálogo Inicial: ---");
        System.out.println(teclado);
        System.out.println(monitor);

        // Cliente monta seu pedido
        ItemPedido item1 = new ItemPedido(teclado, 2); // Compra 2 teclados
        ItemPedido item2 = new ItemPedido(monitor, 1); // Compra 1 monitor

        pedido.adicionarItem(item1);
        pedido.adicionarItem(item2);

        System.out.println("\n" + pedido);

        System.out.println("\n--- Saldos do Estoque Pós-Venda (Atualizados automaticamente): ---");
        System.out.println(teclado); // Deve mostrar 8 em estoque (tinha 10 e vendeu 2)
        System.out.println(monitor); // Deve mostrar 4 em estoque (tinha 5 e vendeu 1)
    }
}