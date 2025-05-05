package com.v1.apiDebt.Io.domain.validators;

import java.util.regex.Pattern;

public class ValidadorTelefone {
    // Atributos
    private static final String PHONE_REGEX = "^(\\d{2})?\\d{4,5}\\d{4}$"; // Para validar telefone sem caracteres especiais
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // Métodos
    public static boolean validaTelefone(String phone) {
        // Verifica se o número de telefone limpo é válido
        return PHONE_PATTERN.matcher(phone).matches();
    }
}