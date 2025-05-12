package com.v1.apiDebt.Io.application.ports.output;

import com.v1.apiDebt.Io.domain.models.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepositoryPort {
    Usuario salvar(Usuario usuario);
    void alterarSenha(UUID id, String senhaAtual, String novaSenha, String confirmarSenha);
    boolean autenticar(String email, String senha);
    Optional<Usuario> buscaPorId(UUID id);
    Optional<Usuario> buscaPorEmail(String email);
    boolean deletar(UUID id);
    Usuario atualizar(Usuario usuario);
    List<Usuario> listarTodosUsuarios();
    boolean confirmarCadastro(String token);
}
