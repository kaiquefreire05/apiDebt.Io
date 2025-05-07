package com.v1.apiDebt.Io.exceptions;

public class EmailInvalidoException extends RuntimeException {
    // Atributos
    private final String code;

    public EmailInvalidoException(String message, String code) {
        super(message);
        this.code = code;
    }

    // Getter
    public String getCode() {
        return code;
    }
}
