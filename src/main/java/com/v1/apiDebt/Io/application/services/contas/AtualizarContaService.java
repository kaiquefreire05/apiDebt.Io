package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.AtualizarContaUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;
import com.v1.apiDebt.Io.domain.models.Contas;

public class AtualizarContaService implements AtualizarContaUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public AtualizarContaService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public Contas atualizar(Contas contas) {
        return contasRepositoryPort.atualizar(contas);
    }
}
