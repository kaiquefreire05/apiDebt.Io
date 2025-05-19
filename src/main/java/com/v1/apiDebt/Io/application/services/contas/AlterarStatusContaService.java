package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.AlterarStatusContaUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;
import com.v1.apiDebt.Io.domain.enums.StatusContaEnum;
import com.v1.apiDebt.Io.domain.models.Contas;

public class AlterarStatusContaService implements AlterarStatusContaUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public AlterarStatusContaService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public Contas alterarStatusConta(Long contaId, StatusContaEnum status) {
        return contasRepositoryPort.alterarStatus(contaId, status);
    }
}
