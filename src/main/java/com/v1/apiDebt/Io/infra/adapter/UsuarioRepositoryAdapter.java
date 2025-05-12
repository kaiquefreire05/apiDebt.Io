package com.v1.apiDebt.Io.infra.adapter;

import com.v1.apiDebt.Io.application.mappers.UsuarioMapper;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.ErroCriacaoUsuarioException;
import com.v1.apiDebt.Io.exceptions.SenhaInvalidaException;
import com.v1.apiDebt.Io.exceptions.UsuarioNaoEncontradoException;
import com.v1.apiDebt.Io.infra.entity.UsuarioEntity;
import com.v1.apiDebt.Io.infra.jpaRepository.UsuarioJpaRepository;
import com.v1.apiDebt.Io.infra.security.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.*;
import static com.v1.apiDebt.Io.infra.util.Utils.logConsole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {
    // Injeção de Dependências
    private final UsuarioJpaRepository repository;
    private final EntradaLogService logService;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository repository, EntradaLogService logService, UsuarioMapper mapper,
                                    PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.logService = logService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    @Override
    public Usuario salvar(Usuario usuario) {
        try {
            logConsole.info("Iniciando rotina de criação de usuário::UsuarioRepositoryAdapter");

            // Criptografando a senha antes de salvar no banco de dados
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

            var usuarioSalvo = repository.save(mapper.deDominioParaEntidade(usuario));
            String mensagemSucesso = "Usuário criado com sucesso. ID: " + usuarioSalvo.getId();

            // Salvando o log de sucesso e retornando o usuário criado
            logService.saveLog("INFO", "UsuarioRepositoryAdapter", mensagemSucesso, "");
            return mapper.deEntidadeParaDominio(usuarioSalvo);

        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro no processo de salvar o usuário, método: salvar" +
                    "::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            throw new ErroCriacaoUsuarioException(USR005.getMessage(), USR005.getCode());
        }
    }

    @Override
    public void alterarSenha(UUID id, String senhaAtual, String novaSenha, String confirmarSenha) {
        try {
            // Encontrando usuário pelo ID
            Optional<UsuarioEntity> usuarioOptional = repository.findById(id);
            if (usuarioOptional.isEmpty()) {
                logConsole.info("Usuário não encontrado, ID: {}", id);
                throw new UsuarioNaoEncontradoException(USR006.getMessage(), USR006.getCode());
            }

            if (!passwordEncoder.matches(senhaAtual, usuarioOptional.get().getSenha())) {
                logConsole.info("Senha atual não confere com a senha do usuário, ID: {}", id);
                throw new SenhaInvalidaException(USR007.getMessage(), USR007.getCode());
            }

            if (!novaSenha.equals(confirmarSenha)) {
                logConsole.info("Nova senha e confirmação de senha não conferem, ID: {}", id);
                throw new SenhaInvalidaException(USR008.getMessage(), USR008.getCode());
            }

            // Atualizando a senha do usuário
            usuarioOptional.get().setSenha(passwordEncoder.encode(novaSenha));
            usuarioOptional.get().setDataAtualizacao(java.time.LocalDateTime.now());
            repository.save(usuarioOptional.get());
            String mensagemSucesso = "Senha atualizada com sucesso, ID: " + id;

            // Salvando o log de sucesso e retornando true
            logService.saveLog("INFO", "UsuarioRepositoryAdapter", mensagemSucesso, "");

        } catch (Exception ex) {
            String mensagemErro = "Ocorreu um erro no processo de alterar a senha do usuário, método: alterarSenha" +
                    "::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
        }
    }

    @Override
    public boolean autenticar(String email, String senha) {
        try {
            // Buscar o usuário pelo e-mail
            Optional<UsuarioEntity> usuarioOptional = repository.findByEmail(email);
            if (usuarioOptional.isEmpty()) {
                logConsole.info("Usuário não encontrado com o e-mail: {}", email);
                return false;
            }

            // Verificar se a senha fornecida corresponde à senha armazenada
            UsuarioEntity usuario = usuarioOptional.get();
            if (!passwordEncoder.matches(senha, usuario.getSenha())) {
                logConsole.info("Senha incorreta para o e-mail: {}", email);
                return false;
            }

            // Verificar se o usuário está ativo
            if (usuario.getAtivo() == null || !usuario.getAtivo()) {
                logConsole.info("Usuário não está ativo, e-mail: {}", email);
                return false;
            }

            // Autenticação bem-sucedida
            logConsole.info("Usuário autenticado com sucesso, e-mail: {}", email);
            return true;

        } catch (Exception ex) {
            String mensagemErro = "Erro ao autenticar o usuário, método: autenticar::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            return false;
        }
    }

    @Override
    public Optional<Usuario> buscaPorId(UUID id) {
        try {
            Optional<UsuarioEntity> usuarioEntity = repository.findById(id);
            return usuarioEntity.map(mapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar usuário por ID, método: buscaPorId::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> buscaPorEmail(String email) {
        try {
            Optional<UsuarioEntity> usuarioEntity = repository.findByEmail(email);
            return usuarioEntity.map(mapper::deEntidadeParaDominio);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao buscar usuário por e-mail, método: buscaPorEmail::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            return Optional.empty();
        }
    }

    @Override
    public boolean deletar(UUID id) {
        try {
            if (!repository.existsById(id)) {
                logConsole.info("Usuário não encontrado para exclusão, ID: {}::UsuarioRepositoryAdapter", id);
                throw new UsuarioNaoEncontradoException(USR006.getMessage(), USR006.getCode());
            }

            repository.deleteById(id);
            String mensagemSucesso = "Usuário deletado com sucesso, ID: " + id;

            // Salvando o log de sucesso
            logService.saveLog("INFO", "UsuarioRepositoryAdapter", mensagemSucesso, "");
            return true;

        } catch (Exception ex) {
            String mensagemErro = "Erro ao deletar usuário, método: deletar::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            return false;
        }
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        try {
            // Verificar se o usuário existe no banco de dados
            if (!repository.existsById(usuario.getId())) {
                logConsole.info("Usuário não encontrado para atualização, ID: {}::UsuarioRepositoryAdapter", usuario.getId());
                throw new UsuarioNaoEncontradoException(USR006.getMessage(), USR006.getCode());
            }

            // Atualizar os dados do usuário
            UsuarioEntity usuarioEntity = mapper.deDominioParaEntidade(usuario);
            usuarioEntity.setDataAtualizacao(java.time.LocalDateTime.now());
            UsuarioEntity usuarioAtualizado = repository.save(usuarioEntity);

            // Log de sucesso
            String mensagemSucesso = "Usuário atualizado com sucesso, ID: " + usuarioAtualizado.getId();
            logService.saveLog("INFO", "UsuarioRepositoryAdapter", mensagemSucesso, "");

            return mapper.deEntidadeParaDominio(usuarioAtualizado);
        } catch (Exception ex) {
            String mensagemErro = "Erro ao atualizar o usuário, método: atualizar::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            throw new ErroCriacaoUsuarioException(USR005.getMessage(), USR005.getCode());
        }
    }

    @Override
    public List<Usuario> listarTodosUsuarios() {
        try {
            List<UsuarioEntity> usuarios = repository.findAll();
            return usuarios.stream()
                    .map(mapper::deEntidadeParaDominio)
                    .toList();
        } catch (Exception ex) {
            String mensagemErro = "Erro ao listar todos os usuários, método: listarTodosUsuarios::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            return List.of();
        }
    }

    @Override
    public boolean confirmarCadastro(String token) {
        try {
            String email = tokenService.validarToken(token);
            if (email == null) {
                logConsole.info("Token inválido ou expirado:");
                return false;
            }

            Optional<UsuarioEntity> usuario = repository.findByEmail(email);
            if (usuario.isEmpty()) {
                logConsole.info("Usuário não encontrado com o e-mail: {}::UsuarioRepositoryAdapter", email);
                return false;
            }

            UsuarioEntity usuarioEntity = usuario.get();
            usuarioEntity.setAtivo(true);
            usuarioEntity.setDataCadastro(LocalDateTime.now());
            repository.save(usuarioEntity);
            String mensagemSucesso = "Cadastro confirmado com sucesso, e-mail: " + email;
            logService.saveLog("INFO", "UsuarioRepositoryAdapter", mensagemSucesso, "");
            return true;

        } catch (Exception ex) {
            String mensagemErro = "Erro ao confirmar cadastro, método: confirmarCadastro::UsuarioRepositoryAdapter";
            logService.saveLog("ERROR", "UsuarioRepositoryAdapter", mensagemErro, ex.toString());
            return false;
        }
    }
}
