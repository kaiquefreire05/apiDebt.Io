package com.v1.apiDebt.Io.application.services;

import com.v1.apiDebt.Io.application.ports.input.usuario.DeletarUsuarioUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;

import java.util.UUID;

public class DeletarUsuarioService implements DeletarUsuarioUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public DeletarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public boolean deletarUsuario(UUID uuid) {
        return usuarioRepositoryPort.deletar(uuid);
    }
}
