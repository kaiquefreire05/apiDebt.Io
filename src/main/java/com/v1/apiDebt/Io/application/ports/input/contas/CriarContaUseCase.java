package com.v1.apiDebt.Io.application.ports.input.contas;

import com.v1.apiDebt.Io.domain.models.Contas;

import java.util.List;

public interface CriarContaUseCase {
    List<Contas> criar(Contas contas);
}
