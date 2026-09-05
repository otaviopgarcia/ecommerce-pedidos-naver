package com.ecommerce.pedidos.naver.modelo;

import java.math.BigDecimal;

/**
 * Representa um produto do catálogo do e-commerce.
 * Responsável por gerenciar seu próprio estado, estoque e preço de forma consistente.
 */
public class Produto {
    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal preco; // Requisito desejável: migrado de double para BigDecimal [7]
    private int quantidadeEmEstoque;
    private boolean ativo;

    public Produto() {
    }

    /**
     * Construtor completo para inicializar o Produto em estado utilizável [16].
     * Os atributos que possuem regras de validação são passados diretamente pelos setters [5, 13].
     */
    public Produto(String codigo, String nome, String descricao, BigDecimal preco, int quantidadeEmEstoque) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código (SKU) do produto é obrigatório.");
        }
        this.codigo = codigo.trim();
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco); // Valida no nascimento [5, 13]
        setQuantidadeEmEstoque(quantidadeEmEstoque); // Valida no nascimento [5, 13]
        this.ativo = true; // Todo produto nasce ativo [17]
    }

    public String getCodigo() {
        return codigo;
    }

    // Nota de Clean Code: setCodigo(String) público foi removido para garantir a imutabilidade do SKU [6, 14].

    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome do produto.
     * @throws IllegalArgumentException se o nome for nulo ou vazio [12].
     */
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * Altera o preço de venda do produto.
     * @throws IllegalArgumentException se o preço for nulo ou menor que zero [10, 12].
     */
    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) { // compareTo é obrigatório para comparar BigDecimal [10, 18]
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    /**
     * Altera diretamente a quantidade física de itens em estoque.
     * @throws IllegalArgumentException se a quantidade informada for negativa [11, 12].
     */
    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        if (quantidadeEmEstoque < 0) {
            throw new IllegalArgumentException("A quantidade física em estoque não pode ser negativa.");
        }
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Verifica se o produto está ativo e se possui estoque disponível [19].
     */
    public boolean temEstoqueDisponivel(int quantidadeDesejada) {
        return ativo && quantidadeEmEstoque >= quantidadeDesejada;
    }

    /**
     * Realiza a baixa do estoque físico após uma venda bem-sucedida [19].
     * @throws IllegalArgumentException se a quantidade for menor ou igual a zero [15].
     * @throws IllegalStateException se não houver saldo suficiente no estoque físico [15].
     */
    public void baixarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para baixa de estoque deve ser estritamente positiva.");
        }
        if (!temEstoqueDisponivel(quantidade)) {
            throw new IllegalStateException("Estoque insuficiente. Saldo atual: " + quantidadeEmEstoque);
        }
        this.quantidadeEmEstoque -= quantidade; // Dedução segura
    }

    @Override
    public String toString() {
        return String.format(
                "[%-10s] %-15s - R$ %,.2f (%6d em estoque)",
                codigo,
                nome,
                preco,
                quantidadeEmEstoque);
    }
}