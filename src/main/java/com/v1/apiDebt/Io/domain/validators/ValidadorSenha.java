package com.v1.apiDebt.Io.domain.validators;

import java.util.List;
import java.util.function.Predicate;

public class ValidadorSenha {

    public static boolean validaSenha(String senha) {
        // Verificação de valor nulo
        if (senha == null) {
            return false;
        }

        // Outras verificações (fácil alterar e remover)
        List<Predicate<String>> validacoes = List.of(
                s -> s.length() >= 8, // Tamanho mínimo de 8 caracteres
                s -> s.matches(".*[A-Z].*"), // Pelo menos uma letra maiúscula
                s -> s.matches(".*[a-z].*"), // Pelo menos uma letra minúscula
                s -> s.matches(".*\\d.*"), // Pelo menos um número
                s -> s.matches(".*[@#$%^&+=!].*") // Pelo menos um caractere especial
        );

        return validacoes.stream().allMatch(validador -> validador.test(senha));
    }
}
