package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Boleto extends FormaPagamento {

    private String codigoDeBarras;
    private LocalDate dataDeVencimento;

    public Boleto(
            BigDecimal valor,
            String codigoDeBarras,
            LocalDate dataDeVencimento) {

        super(valor);
        setCodigoDeBarras(codigoDeBarras);
        setDataDeVencimento(dataDeVencimento);
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        if (codigoDeBarras == null || codigoDeBarras.isBlank()) {
            throw new IllegalArgumentException(
                "Código de barras é obrigatório."
            );
        }

        this.codigoDeBarras = codigoDeBarras.trim();
    }

    public LocalDate getDataDeVencimento() {
        return dataDeVencimento;
    }

    public void setDataDeVencimento(LocalDate dataDeVencimento) {
        if (dataDeVencimento == null
                || dataDeVencimento.isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                "Data de vencimento não pode ser no passado."
            );
        }

        this.dataDeVencimento = dataDeVencimento;
    }

    @Override
    public boolean processar() {
        System.out.println(
            "Gerando código de barras bancário... GERADO!"
        );

        return true;
    }

    @Override
    public String getResumo() {
        return super.getResumo()
            + " [Boleto Vencimento: "
            + dataDeVencimento
            + "]";
    }
}
