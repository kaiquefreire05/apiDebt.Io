package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.DeletarContaUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;

public class DeletarContaService implements DeletarContaUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public DeletarContaService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public boolean deletarConta(Long idConta) {
        return contasRepositoryPort.deletarConta(idConta);
    }
}
