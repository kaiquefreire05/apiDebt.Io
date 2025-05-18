package com.v1.apiDebt.Io.exceptions;

public class ErroCriacaoContaException extends RuntimeException {
    // Atributos
    private final String code;

    public ErroCriacaoContaException(String message, String code) {
        super(message);
        this.code = code;
    }

    // Getter
    public String getCode() {
        return code;
    }
}
