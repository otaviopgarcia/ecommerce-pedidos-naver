package com.ecommerce.pedidos.naver.persistencia;

import com.ecommerce.pedidos.naver.modelo.ItemPedido;
import com.ecommerce.pedidos.naver.modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidoTransacaoDao {

    private final ProdutoDao produtoDao = new ProdutoDao();

    public void registrarPedidoComBaixaEstoque(Long clienteId, Pedido pedido) throws Exception {
        Connection conn = ConexaoFactory.obterConexao();
        boolean autoCommitOriginal = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false);

            for (ItemPedido item : pedido.getItens()) {
                int estoqueAtual = item.getProduto().getQuantidadeEstoque();
                if (estoqueAtual < item.getQuantidade()) {
                    throw new IllegalStateException("Estoque insuficiente para " + item.getProduto().getCodigo());
                }
                int novoEstoque = estoqueAtual - item.getQuantidade();
                produtoDao.atualizarEstoque(conn, item.getProduto().getCodigo(), novoEstoque);
                item.getProduto().setQuantidadeEstoque(novoEstoque);
            }

            String sqlPedido = "INSERT INTO pedido (numero, cliente_id, situacao, valor_total) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlPedido)) {
                ps.setString(1, pedido.getNumero());
                ps.setLong(2, clienteId);
                ps.setString(3, pedido.getSituacao().name());
                ps.setBigDecimal(4, pedido.calcularValorTotal());
                ps.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                e.addSuppressed(rollbackEx);
            }
            throw e;
        } finally {
            try {
                conn.setAutoCommit(autoCommitOriginal);
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}