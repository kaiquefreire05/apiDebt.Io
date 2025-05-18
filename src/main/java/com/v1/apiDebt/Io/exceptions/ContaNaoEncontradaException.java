package com.v1.apiDebt.Io.exceptions;

public class ContaNaoEncontradaException extends RuntimeException {
    // Atributos
    private final String code;

    public ContaNaoEncontradaException(String message, String code) {
        super(message);
        this.code = code;
    }

    // Getter
    public String getCode() {
        return code;
    }
}
