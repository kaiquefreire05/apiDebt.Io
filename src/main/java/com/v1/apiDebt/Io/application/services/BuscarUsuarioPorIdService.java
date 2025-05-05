package com.v1.apiDebt.Io.application.services;

import com.v1.apiDebt.Io.application.ports.input.usuario.BuscarUsuarioPorIdUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.UsuarioNaoEncontradoException;

import java.util.UUID;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.USR006;

public class BuscarUsuarioPorIdService implements BuscarUsuarioPorIdUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public BuscarUsuarioPorIdService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario buscarPorId(UUID uuid) {
        return usuarioRepositoryPort.buscaPorId(uuid)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(USR006.getMessage(), USR006.getCode()));
    }
}
