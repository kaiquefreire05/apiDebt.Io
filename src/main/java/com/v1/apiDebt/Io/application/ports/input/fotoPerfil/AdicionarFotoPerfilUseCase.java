package com.v1.apiDebt.Io.application.ports.input.fotoPerfil;

import java.util.UUID;

public interface AdicionarFotoPerfilUseCase {
    Boolean adicionarFotoPerfil(UUID idUsuario, String fotoBase64);
}
