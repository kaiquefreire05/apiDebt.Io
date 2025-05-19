package com.v1.apiDebt.Io.web.dto.request;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.TipoPagamentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CriarContaRequest(
        @NotNull UUID usuarioId,
        @NotBlank String nomeCompra,
        @NotNull @Positive BigDecimal valor,
        @NotNull TipoPagamentoEnum tipoPagamento,
        @NotNull CategoriasEnum categoria,
        @NotNull LocalDate dataVencimento,
        @NotNull Boolean contaRecorrente
) {
}
