package com.v1.apiDebt.Io.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AlterarSenhaRequest(@NotNull UUID id,
                                  @NotBlank String senhaAtual,
                                  @NotBlank String novaSenha,
                                  @NotBlank String confirmarSenha) {
}
