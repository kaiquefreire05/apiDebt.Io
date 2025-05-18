package com.v1.apiDebt.Io.exceptions;

public class ErroAtualizacaoContaException extends RuntimeException {
    // Atributos
    private final String code;

    public ErroAtualizacaoContaException(String message, String code) {
        super(message);
        this.code = code;
    }

    // Getter
    public String getCode() {
        return code;
    }
}
