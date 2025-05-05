package com.v1.apiDebt.Io.application.ports.input.usuario;

import java.util.UUID;

public interface DeletarUsuarioUseCase {
    boolean deletarUsuario(UUID uuid);
}
