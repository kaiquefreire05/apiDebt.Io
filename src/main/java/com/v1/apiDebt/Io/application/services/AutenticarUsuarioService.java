package com.v1.apiDebt.Io.application.services;

import com.v1.apiDebt.Io.application.ports.input.usuario.AutenticarUsuarioUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;

public class AutenticarUsuarioService implements AutenticarUsuarioUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public AutenticarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public boolean autenticar(String email, String senha) {
        return usuarioRepositoryPort.autenticar(email, senha);
    }
}
