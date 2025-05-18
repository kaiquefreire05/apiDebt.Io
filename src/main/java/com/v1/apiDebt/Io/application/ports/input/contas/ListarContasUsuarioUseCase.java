package com.v1.apiDebt.Io.application.ports.input.contas;

import com.v1.apiDebt.Io.domain.models.Contas;

import java.util.List;
import java.util.UUID;

public interface ListarContasUsuarioUseCase {
    List<Contas> listarContasUsuario(UUID idUsuario);
}
