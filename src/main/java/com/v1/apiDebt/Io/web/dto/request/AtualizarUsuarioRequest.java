package com.v1.apiDebt.Io.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record AtualizarUsuarioRequest(@NotNull UUID id,
                                      @NotBlank String nome,
                                      @NotBlank String sobrenome,
                                      @NotBlank String email,
                                      @NotBlank String cpf,
                                      @NotBlank String telefone,
                                      @NotNull @Positive Integer percentualGastos) {
}
