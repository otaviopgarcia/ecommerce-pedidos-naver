package com.ecommerce.pedidos.naver.modelo;

import java.math.BigDecimal;

import com.ecommerce.pedidos.naver.excecao.EstoqueInsuficienteException;

public class Produto {

    private String codigo;
    private String nome;
    private BigDecimal preco;
    private int quantidadeEstoque;
    private boolean ativo;

    public Produto(String codigo, String nome, BigDecimal preco, int quantidadeEstoque) {
        setCodigo(codigo);
        setNome(nome);
        setPreco(preco);
        setQuantidadeEstoque(quantidadeEstoque);
        this.ativo = true;
    }

    public void baixarEstoque(int quantidade) throws EstoqueInsuficienteException {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade para baixa deve ser positiva.");
        }
        if (quantidade > this.quantidadeEstoque) {
            throw new EstoqueInsuficienteException(this, quantidade);
        }
        this.quantidadeEstoque -= quantidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("Codigo nao pode ser vazio.");
        }
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto nao pode ser vazio.");
        }
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preco nao pode ser nulo nem negativo.");
        }
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque nao pode ser negativa.");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}