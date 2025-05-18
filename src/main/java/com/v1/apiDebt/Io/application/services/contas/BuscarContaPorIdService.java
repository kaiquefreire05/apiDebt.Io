package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.BuscarContaPorIdUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;
import com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum;
import com.v1.apiDebt.Io.domain.models.Contas;
import com.v1.apiDebt.Io.exceptions.ContaNaoEncontradaException;

import java.util.UUID;

public class BuscarContaPorIdService implements BuscarContaPorIdUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public BuscarContaPorIdService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public Contas buscarPorId(Long id) {
        return contasRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ContaNaoEncontradaException(ErrorCodeEnum.CON002.getMessage(),
                        ErrorCodeEnum.CON002.getCode()));
    }
}
