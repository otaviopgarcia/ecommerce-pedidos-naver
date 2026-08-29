package com.ecommerce.pedidos.naver.modelo;

public class Produto {
    private String codigo;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEmEstoque;
    private boolean ativo;

    // Construtor padrão
    public Produto() {
    }

    // Construtor com parâmetros obrigatórios (o produto já nasce ativo)
    public Produto(String codigo, String nome, String descricao, double preco, int quantidadeEmEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.ativo = true; 
    }

    // Getters e Setters (repare que NÃO temos setCodigo para proteger o SKU)
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // Método de negócio: Verifica se há estoque suficiente
    public boolean temEstoqueDisponivel(int quantidadeDesejada) {
        return ativo && quantidadeEmEstoque >= quantidadeDesejada;
    }

    // Método de negócio: Baixa o estoque físico
    public void baixarEstoque(int quantidade) {
        this.quantidadeEmEstoque = this.quantidadeEmEstoque - quantidade;
    }

    @Override
    public String toString() {
        return String.format(
                "[%-10s] %-15s - R$ %9.2f (%6d em estoque)",
                codigo,
                nome,
                preco,
                quantidadeEmEstoque);
    }
}