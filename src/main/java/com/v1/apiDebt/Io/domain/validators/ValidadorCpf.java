package com.v1.apiDebt.Io.domain.validators;

import java.util.regex.Pattern;

public class ValidadorCpf {
    private static final Pattern SOMENTE_DIGITOS = Pattern.compile("\\d{11}");

    public static boolean cpfValido(String cpf) {
        if (cpf == null) return false;

        // Remove tudo que não for dígito (pontos, traços, espaços, etc.)
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se tem exatamente 11 dígitos numéricos
        if (!SOMENTE_DIGITOS.matcher(cpf).matches()) return false;

        return digitoCpfValido(cpf);
    }

    private static boolean digitoCpfValido(String cpf) {
        // CPF inválido se todos os dígitos forem iguais (ex: 11111111111)
        if (cpf.chars().distinct().count() == 1) return false;

        int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int firstDigit = calcularDigito(cpf.substring(0, 9), weights);
        int secondDigit = calcularDigito(cpf.substring(0, 9) + firstDigit,
                                         new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2});

        return cpf.equals(cpf.substring(0, 9) + firstDigit + secondDigit);
    }

    private static int calcularDigito(String str, int[] weights) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += Character.getNumericValue(str.charAt(i)) * weights[i];
        }
        int mod = sum % 11;
        return mod < 2 ? 0 : 11 - mod;
    }
}