package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.SaldoRestanteUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;

import java.math.BigDecimal;
import java.util.UUID;

public class SaldoRestanteService implements SaldoRestanteUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public SaldoRestanteService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public BigDecimal somarGasto(UUID uuid) {
        return contasRepositoryPort.gastoDisponivel(uuid);
    }
}
