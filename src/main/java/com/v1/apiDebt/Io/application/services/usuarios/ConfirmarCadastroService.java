package com.v1.apiDebt.Io.application.services.usuarios;

import com.v1.apiDebt.Io.application.ports.input.usuario.ConfirmarCadastroUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;

public class ConfirmarCadastroService implements ConfirmarCadastroUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public ConfirmarCadastroService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public boolean confirmarCadastro(String token) {
        return usuarioRepositoryPort.confirmarCadastro(token);
    }
}
