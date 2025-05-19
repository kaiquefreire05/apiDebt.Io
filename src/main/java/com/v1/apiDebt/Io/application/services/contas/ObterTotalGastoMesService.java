package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.ObterTotalGastoMesUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;

import java.math.BigDecimal;
import java.util.UUID;

public class ObterTotalGastoMesService implements ObterTotalGastoMesUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public ObterTotalGastoMesService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public BigDecimal obterTotalGastoMes(UUID usuarioId) {
        return contasRepositoryPort.obterTotalGastoMes(usuarioId);
    }
}
