package com.ecommerce.pedidos.naver.persistencia;

import com.ecommerce.pedidos.naver.modelo.Produto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDao {

    public void inserir(Connection conn, Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (codigo, nome, preco, quantidade_estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getCodigo());
            ps.setString(2, produto.getNome());
            ps.setBigDecimal(3, produto.getPreco());
            ps.setInt(4, produto.getQuantidadeEstoque());
            ps.executeUpdate();
        }
    }

    public Optional<Produto> buscarPorCodigo(Connection conn, String codigo) throws SQLException {
        String sql = "SELECT codigo, nome, preco, quantidade_estoque FROM produto WHERE codigo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String cod = rs.getString("codigo");
                    String nome = rs.getString("nome");
                    BigDecimal preco = rs.getBigDecimal("preco");
                    int estoque = rs.getInt("quantidade_estoque");
                    return Optional.of(new Produto(cod, nome, preco, estoque));
                }
                return Optional.empty();
            }
        }
    }

    public List<Produto> listarTodos(Connection conn) throws SQLException {
        String sql = "SELECT codigo, nome, preco, quantidade_estoque FROM produto ORDER BY codigo";
        List<Produto> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getBigDecimal("preco"),
                        rs.getInt("quantidade_estoque")
                ));
            }
        }
        return lista;
    }

    public int atualizarEstoque(Connection conn, String codigo, int novaQuantidade) throws SQLException {
        String sql = "UPDATE produto SET quantidade_estoque = ? WHERE codigo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, novaQuantidade);
            ps.setString(2, codigo);
            return ps.executeUpdate();
        }
    }

    public int excluirPorCodigo(Connection conn, String codigo) throws SQLException {
        String sql = "DELETE FROM produto WHERE codigo = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate();
        }
    }
}