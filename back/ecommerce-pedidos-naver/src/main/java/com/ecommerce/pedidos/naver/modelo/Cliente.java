package com.ecommerce.pedidos.naver.modelo;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;

    public Cliente() {}

    public Cliente(String nome, String cpf, String email, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    // CPF também é imutável após o cadastro, por isso não criamos setCpf

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    // Método de negócio sugerido pelo roteiro
    public String getIdentificacao() {
        return String.format("%s (CPF: %s)", this.nome, this.cpf);
    }

    @Override
    public String toString() {
        return String.format("Cliente: %s | E-mail: %s | Endereço: %s", nome, email, endereco);
    }
}