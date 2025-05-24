package com.v1.apiDebt.Io.application.services.foto;

import com.v1.apiDebt.Io.application.ports.input.fotoPerfil.AlterarFotoPerfilUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;

import java.util.UUID;

public class AlterarFotoPerfilService implements AlterarFotoPerfilUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepository;

    public AlterarFotoPerfilService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public Boolean alterarFotoPerfil(UUID idUsuario, String fotoBase64) {
        return usuarioRepository.alterarFotoPerfil(idUsuario, fotoBase64);
    }
}
