package com.v1.apiDebt.Io.application.services.usuarios;

import com.v1.apiDebt.Io.application.ports.input.usuario.BuscarUsuarioPorEmailUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.UsuarioNaoEncontradoException;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.*;

public class BuscarUsuarioPorEmailService implements BuscarUsuarioPorEmailUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public BuscarUsuarioPorEmailService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepositoryPort.buscaPorEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(USR006.getMessage(), USR006.getCode()));
    }
}
