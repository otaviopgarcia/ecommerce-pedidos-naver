package com.ecommerce.pedidos.naver.modelo;

public abstract class Pessoa {
    private String nome;
    private String documento; // CPF ou CNPJ

    /**
     * O construtor obriga que qualquer pessoa nasça com nome e documento validados [20].
     */
    public Pessoa(String nome, String documento) {
        setNome(nome);
        setDocumento(documento);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é de preenchimento obrigatório.");
        }
        this.nome = nome.trim();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("O documento de identificação é obrigatório.");
        }
        // Requisito desejável: Validação de formato (Apenas dígitos numéricos limpos) [7, 12]
        String apenasDigitos = documento.replaceAll("\\D", "");
        if (apenasDigitos.length() != 11 && apenasDigitos.length() != 14) {
            throw new IllegalArgumentException("Documento inválido. Deve conter 11 dígitos (CPF) ou 14 dígitos (CNPJ).");
        }
        this.documento = apenasDigitos;
    }

    /**
     * Método abstrato obrigatório. Cada subclasse concreta fornecerá sua própria identificação [20, 23].
     */
    public abstract String getIdentificacao();
}
