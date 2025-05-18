package com.v1.apiDebt.Io.web.dto.response;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.TipoPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContasResponse(
        String nomeCompra,
        BigDecimal valor,
        TipoPagamentoEnum tipoPagamento,
        CategoriasEnum categoria,
        LocalDate dataVencimento,
        LocalDateTime dataPagamento,
        Boolean contaRecorrente,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {}
