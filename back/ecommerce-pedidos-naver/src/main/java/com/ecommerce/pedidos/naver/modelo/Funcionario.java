package com.ecommerce.pedidos.naver.modelo;

public class Funcionario extends Pessoa {
    private String matricula;
    private String cargo;

    public Funcionario(String nome, String cpf, String matricula, String cargo) {
        super(nome, cpf);
        setMatricula(matricula);
        setCargo(cargo);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("A matrícula é obrigatória.");
        }
        this.matricula = matricula.trim();
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        if (cargo == null || cargo.isBlank()) {
            throw new IllegalArgumentException("O cargo é obrigatório.");
        }
        this.cargo = cargo.trim();
    }

    @Override
    public String getIdentificacao() {
        return String.format("%s - Matrícula: %s (%s)", getNome(), matricula, cargo);
    }
}