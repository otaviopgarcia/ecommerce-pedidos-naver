package com.ecommerce.pedidos.naver.modelo.pagamento;

import java.math.BigDecimal;

public class Pix extends FormaPagamento {

    private String chave;
    private String tipoDaChave;

    public Pix(
            BigDecimal valor,
            String chave,
            String tipoDaChave) {

        super(valor);
        setTipoDaChave(tipoDaChave);
        setChave(chave);
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        if (chave == null || chave.isBlank()) {
            throw new IllegalArgumentException(
                "Chave Pix é obrigatória."
            );
        }

        this.chave = chave.trim();
    }

    public String getTipoDaChave() {
        return tipoDaChave;
    }

    public void setTipoDaChave(String tipoDaChave) {
        if (tipoDaChave == null || tipoDaChave.isBlank()) {
            throw new IllegalArgumentException(
                "Tipo da chave é obrigatório."
            );
        }

        this.tipoDaChave = tipoDaChave.trim();
    }

    @Override
    public boolean processar() {
        System.out.println(
            "Gerando QR Code Pix... PAGO INSTANTANEAMENTE!"
        );

        return true;
    }

    @Override
    public String getResumo() {
        return super.getResumo()
            + " [Pix Chave "
            + tipoDaChave
            + ": "
            + chave
            + "]";
    }
}
