package com.v1.apiDebt.Io.application.ports.input.contas;

import com.v1.apiDebt.Io.domain.models.Contas;

public interface BuscarContaPorIdUseCase {
    Contas buscarPorId(Long id);
}
