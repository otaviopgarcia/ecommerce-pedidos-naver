
import com.ecommerce.pedidos.naver.modelo.Produto;
import com.ecommerce.pedidos.naver.modelo.Cliente;
import com.ecommerce.pedidos.naver.modelo.ItemPedido;
import com.ecommerce.pedidos.naver.modelo.Pedido;

public class Aplicacao {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE PEDIDOS ===");

        // 1. Cadastrar os produtos no catálogo da loja
        Produto teclado = new Produto("TEC01", "Teclado Mecânico", "Teclado RGB Switch Blue", 150.00, 10);
        Produto monitor = new Produto("MON02", "Monitor Ultra", "Monitor Ultrawide 29 IPS", 899.90, 5);
        
        System.out.println("\n--- Catálogo de Produtos Inicial: ---");
        System.out.println(teclado);
        System.out.println(monitor);

        // 2. Cadastrar o cliente comprador
        Cliente cliente = new Cliente("Ana Souza", "123.456.789-00", "ana.souza@email.com", "11 99999-9999", "Rua das Flores, 123");
        System.out.println("\n" + cliente);

        // 3. Criar o pedido (Exemplo: número gerado manualmente ou com PedidoUtils)
        Pedido pedido = new Pedido("PED-2026-00001", cliente, "28/08/2026");

        // 4. Cliente seleciona itens e adiciona ao pedido
        ItemPedido item1 = new ItemPedido(teclado, 2); // Quer comprar 2 teclados
        ItemPedido item2 = new ItemPedido(monitor, 1); // Quer comprar 1 monitor

        System.out.println("\n--- Processando carrinho de compras... ---");
        pedido.adicionarItem(item1);
        pedido.adicionarItem(item2);

        // 5. Imprime o Recibo/Cupom Fiscal completo do Pedido
        System.out.println("\n" + pedido);

        // 6. Provar que o estoque foi atualizado em tempo real após a compra
        System.out.println("\n--- Estoque pós-pedido (Atualizado automaticamente): ---");
        System.out.println(teclado); // Deve mostrar 8 em estoque (tinha 10 e vendeu 2)
        System.out.println(monitor); // Deve mostrar 4 em estoque (tinha 5 e vendeu 1)
    }
}