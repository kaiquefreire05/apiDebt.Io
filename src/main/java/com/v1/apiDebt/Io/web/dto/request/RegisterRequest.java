package com.v1.apiDebt.Io.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterRequest(
    @NotBlank String nome,
    @NotBlank String sobrenome,
    @NotBlank String email,
    @NotBlank String senha,
    @NotBlank String cpf,
    @NotBlank String telefone,
    @NotNull LocalDate dataNascimento,
    @NotNull @Positive BigDecimal rendaMensal
) {}
