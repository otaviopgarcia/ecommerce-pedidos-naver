package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartaoCredito extends FormaPagamento {

    private String numeroMascarado;
    private String bandeira;
    private int quantidadeDeParcelas;

    public CartaoCredito(
            BigDecimal valor,
            String numeroMascarado,
            String bandeira,
            int quantidadeDeParcelas) {

        super(valor);
        setNumeroMascarado(numeroMascarado);
        setBandeira(bandeira);
        setQuantidadeDeParcelas(quantidadeDeParcelas);
    }

    public String getNumeroMascarado() {
        return numeroMascarado;
    }

    public void setNumeroMascarado(String numeroMascarado) {
        if (numeroMascarado == null || numeroMascarado.isBlank()) {
            throw new IllegalArgumentException(
                "Número do cartão é obrigatório."
            );
        }

        this.numeroMascarado = numeroMascarado.trim();
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        if (bandeira == null || bandeira.isBlank()) {
            throw new IllegalArgumentException(
                "Bandeira do cartão é obrigatória."
            );
        }

        this.bandeira = bandeira.trim();
    }

    public int getQuantidadeDeParcelas() {
        return quantidadeDeParcelas;
    }

    public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
        if (quantidadeDeParcelas < 1 || quantidadeDeParcelas > 12) {
            throw new IllegalArgumentException(
                "Quantidade de parcelas deve ser entre 1 e 12."
            );
        }

        this.quantidadeDeParcelas = quantidadeDeParcelas;
    }

    @Override
    public boolean processar() {
        System.out.println(
            "Enviando transação à adquirente do Cartão ("
            + bandeira
            + ")... APROVADO!"
        );

        return true;
    }

    @Override
    public String getResumo() {
        BigDecimal valorParcela = getValor().divide(
            BigDecimal.valueOf(quantidadeDeParcelas),
            2,
            RoundingMode.HALF_UP
        );

        return super.getResumo()
            + String.format(
                " [Cartão %s %s - %dx de R$ %,.2f]",
                bandeira,
                numeroMascarado,
                quantidadeDeParcelas,
                valorParcela
            );
    }
}