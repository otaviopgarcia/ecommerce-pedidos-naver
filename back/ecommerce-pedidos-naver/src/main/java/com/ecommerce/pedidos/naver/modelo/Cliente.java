package com.ecommerce.pedidos.naver.modelo;

/**
 * Representa o cliente que consome os produtos na loja [24].
 * Estende os comportamentos e atributos comuns de Pessoa [25].
 */
public class Cliente extends Pessoa {
    private String email;
    private String telefone;
    private String endereco;

    public Cliente(String nome, String cpf, String email, String telefone, String endereco) {
        super(nome, cpf); // Chama obrigatoriamente o construtor da mãe na primeira linha [25, 26]
        setEmail(email);
        setTelefone(telefone);
        setEndereco(endereco);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Requisito desejável: validação de formato básico contendo '@' [7, 12]
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("O endereço de e-mail informado é inválido.");
        }
        this.email = email.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Implementação obrigatória da identificação pessoal abstrata [25, 26].
     */
    @Override
    public String getIdentificacao() {
        return String.format("%s (Documento: %s)", getNome(), getDocumento());
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s | E-mail: %s | Telefone: %s", getNome(), email, telefone);
    }
}