package com.v1.apiDebt.Io.application.ports.input.usuario;

import java.util.UUID;

public interface AlterarSenhaUseCase {
    void alterarSenha(UUID id, String senhaAtual, String novaSenha, String confirmarSenha);
}
