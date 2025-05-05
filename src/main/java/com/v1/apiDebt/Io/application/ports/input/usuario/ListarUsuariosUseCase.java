package com.v1.apiDebt.Io.application.ports.input.usuario;

import com.v1.apiDebt.Io.domain.models.Usuario;

import java.util.List;

public interface ListarUsuariosUseCase {
    List<Usuario> listarTodosUsuarios();
}
