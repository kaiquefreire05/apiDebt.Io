package com.v1.apiDebt.Io.application.ports.input.usuario;

public interface AutenticarUsuarioUseCase {
    boolean autenticar(String email, String senha);
}
