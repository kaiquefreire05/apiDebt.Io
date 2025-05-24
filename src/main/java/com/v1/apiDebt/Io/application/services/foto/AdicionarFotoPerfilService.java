package com.v1.apiDebt.Io.application.services.foto;

import com.v1.apiDebt.Io.application.ports.input.fotoPerfil.AdicionarFotoPerfilUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;

import java.util.UUID;

public class AdicionarFotoPerfilService implements AdicionarFotoPerfilUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepository;

    public AdicionarFotoPerfilService(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Boolean adicionarFotoPerfil(UUID idUsuario, String fotoBase64) {
        return usuarioRepository.adicionarFotoPerfil(idUsuario, fotoBase64);
    }
}
