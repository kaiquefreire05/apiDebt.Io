package com.v1.apiDebt.Io.web.dto.request;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.TipoPagamentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AtualizarContaRequest(
        @NotNull Long id,
        @NotBlank String nomeCompra,
        @NotNull @Positive BigDecimal valor,
        @NotBlank TipoPagamentoEnum tipoPagamento,
        @NotBlank CategoriasEnum categoria,
        @NotNull String dataVencimento,
        //@NotNull UUID usuarioId,
        @NotNull boolean contaRecorrente
) {
}