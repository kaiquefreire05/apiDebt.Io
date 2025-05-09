package com.v1.apiDebt.Io.domain.enums;

public enum CategoriasEnum {
    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    EDUCACAO("Educação"),
    SAUDE("Saúde"),
    LAZER("Lazer"),
    MORADIA("Moradia"),
    SERVICOS("Serviços"),
    VESTUARIO("Vestuário"),
    TECNOLOGIA("Tecnologia"),
    VIAGEM("Viagem"),
    INVESTIMENTOS("Investimentos"),
    IMPOSTOS("Impostos"),
    OUTROS("Outros");

    private final String descricao;

    CategoriasEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
