package com.v1.apiDebt.Io.exceptions;

public class CpfRegistradoException extends RuntimeException {
    // Atributos
    private final String code;

    public CpfRegistradoException(String message, String code) {
        super(message);
        this.code = code;
    }

    // Getter
    public String getCode() {
        return code;
    }
}
