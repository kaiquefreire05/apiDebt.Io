package com.v1.apiDebt.Io.application.services.usuarios;

import com.v1.apiDebt.Io.application.ports.input.usuario.ListarUsuariosUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;
import com.v1.apiDebt.Io.domain.models.Usuario;

import java.util.List;

public class ListarUsuariosService implements ListarUsuariosUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public ListarUsuariosService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepositoryPort.listarTodosUsuarios();
    }
}
