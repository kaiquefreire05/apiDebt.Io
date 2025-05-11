package com.v1.apiDebt.Io.domain.enums;

public enum TipoPagamentoEnum {
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    BOLETO("Boleto"),
    PIX("Pix"),
    DINHEIRO("Dinheiro");

    private final String descricao;

    TipoPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
