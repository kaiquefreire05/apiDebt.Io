package com.v1.apiDebt.Io.domain.validators;

import java.util.regex.Pattern;

public class ValidadorEmail {
    // Atributos
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Métodos
    public static boolean emailValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
