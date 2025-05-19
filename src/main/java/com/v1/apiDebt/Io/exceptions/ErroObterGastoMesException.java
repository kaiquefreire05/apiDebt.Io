package com.v1.apiDebt.Io.exceptions;

public class ErroObterGastoMesException extends RuntimeException {
    // Atributos
    private final String code;

    public ErroObterGastoMesException(String message, String code) {
        super(message);
        this.code = code;
    }

    // Getter
    public String getCode() {
        return code;
    }
}
