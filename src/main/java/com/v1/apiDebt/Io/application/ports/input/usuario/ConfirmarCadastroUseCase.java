package com.v1.apiDebt.Io.application.ports.input.usuario;

import java.util.UUID;

public interface ConfirmarCadastroUseCase {
    boolean confirmarCadastro(String token);
}
