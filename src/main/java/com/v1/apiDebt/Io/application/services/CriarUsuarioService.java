package com.v1.apiDebt.Io.application.services;

import com.v1.apiDebt.Io.application.mappers.UsuarioMapper;
import com.v1.apiDebt.Io.application.ports.input.usuario.CriarUsuarioUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeCpfPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeEmailPort;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.CpfRegistradoException;
import com.v1.apiDebt.Io.exceptions.EmailCadastradoException;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.*;

public class CriarUsuarioService implements CriarUsuarioUseCase {
    // Injeção de dependência
    private final UsuarioRepositoryPort usuarioRepository;
    private final DisponibilidadeCpfPort disponibilidadeCpfPort;
    private final DisponibilidadeEmailPort disponibilidadeEmailPort;

    public CriarUsuarioService(UsuarioRepositoryPort usuarioRepository, DisponibilidadeCpfPort disponibilidadeCpfPort,
                               DisponibilidadeEmailPort disponibilidadeEmailPort) {
        this.usuarioRepository = usuarioRepository;
        this.disponibilidadeCpfPort = disponibilidadeCpfPort;
        this.disponibilidadeEmailPort = disponibilidadeEmailPort;
    }

    @Override
    public Usuario criar(Usuario usuario) {
        validarDisponibilidadeCpf(usuario.getCpf());
        validarDisponibilidadeEmail(usuario.getEmail());
        return usuarioRepository.salvar(usuario);
    }

    // Métodos auxiliares
    private void validarDisponibilidadeCpf(String cpf) {
        if (disponibilidadeCpfPort.cpfJaCadastrado(cpf)) {
            throw new CpfRegistradoException(USR009.getMessage(), USR009.getCode());
        }
    }

    private void validarDisponibilidadeEmail(String email) {
        if (disponibilidadeEmailPort.emailJaCadastrado(email)) {
            throw new EmailCadastradoException(USR010.getMessage(), USR010.getCode());
        }
    }
}
