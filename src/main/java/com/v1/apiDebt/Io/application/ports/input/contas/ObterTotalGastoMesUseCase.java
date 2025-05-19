package com.v1.apiDebt.Io.application.ports.input.contas;

import java.math.BigDecimal;
import java.util.UUID;

public interface ObterTotalGastoMesUseCase {
    BigDecimal obterTotalGastoMes(UUID usuarioId);
}
