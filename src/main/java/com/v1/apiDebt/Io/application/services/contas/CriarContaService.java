package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.CriarContaUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;
import com.v1.apiDebt.Io.domain.models.Contas;

import java.util.List;

public class CriarContaService implements CriarContaUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public CriarContaService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public List<Contas> criar(Contas contas) {
        return contasRepositoryPort.criar(contas);
    }
}
