package com.v1.apiDebt.Io.application.services.contas;

import com.v1.apiDebt.Io.application.ports.input.contas.ListarContasUsuarioUseCase;
import com.v1.apiDebt.Io.application.ports.output.ContasRepositoryPort;
import com.v1.apiDebt.Io.domain.models.Contas;

import java.util.List;
import java.util.UUID;

public class ListarContasUsuarioService implements ListarContasUsuarioUseCase {
    // Injeção de Dependência
    private final ContasRepositoryPort contasRepositoryPort;

    public ListarContasUsuarioService(ContasRepositoryPort contasRepositoryPort) {
        this.contasRepositoryPort = contasRepositoryPort;
    }

    @Override
    public List<Contas> listarContasUsuario(UUID idUsuario) {
        return contasRepositoryPort.listarContasPorUsuario(idUsuario);
    }
}
