package com.v1.apiDebt.Io.application.ports.input.fotoPerfil;

import java.util.UUID;

public interface AlterarFotoPerfilUseCase {
    Boolean alterarFotoPerfil(UUID idUsuario, String fotoBase64);
}
