package com.v1.apiDebt.Io.application.services.usuarios;

import com.v1.apiDebt.Io.application.ports.input.usuario.AlterarSenhaUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;

import java.util.UUID;

public class AlterarSenhaService implements AlterarSenhaUseCase {
    // Injeção de Dependência
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public AlterarSenhaService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public void alterarSenha(UUID id, String senhaAtual, String novaSenha, String confirmarSenha) {
        usuarioRepositoryPort.alterarSenha(id, senhaAtual, novaSenha, confirmarSenha);
    }
}
