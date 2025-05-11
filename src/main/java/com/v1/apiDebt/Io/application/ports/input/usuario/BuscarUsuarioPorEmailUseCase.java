package com.v1.apiDebt.Io.application.ports.input.usuario;

import com.v1.apiDebt.Io.domain.models.Usuario;

public interface BuscarUsuarioPorEmailUseCase {
    Usuario buscarPorEmail(String email);
}
