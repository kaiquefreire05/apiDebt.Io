package com.v1.apiDebt.Io.exceptions;

public class SenhaIncorretaException extends RuntimeException {
    // Atributos
    private final String code;

    public SenhaIncorretaException(String message, String code) {
        super(message);
        this.code = code;
    }

    // Getter
    public String getCode() {
        return code;
    }
}
