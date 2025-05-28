package com.v1.apiDebt.Io.application.services.usuarios;

import com.v1.apiDebt.Io.application.ports.input.usuario.AtualizarUsuarioUseCase;
import com.v1.apiDebt.Io.application.ports.input.usuario.BuscarUsuarioPorIdUseCase;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeCpfPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeEmailPort;
import com.v1.apiDebt.Io.domain.models.Usuario;
import com.v1.apiDebt.Io.exceptions.CpfRegistradoException;
import com.v1.apiDebt.Io.exceptions.EmailCadastradoException;
import org.springframework.stereotype.Service;

import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.USR009;
import static com.v1.apiDebt.Io.domain.enums.ErrorCodeEnum.USR010;

public class AtualizarUsuarioService implements AtualizarUsuarioUseCase {
    // Injeção de dependência
    private final UsuarioRepositoryPort usuarioRepository;
    private final DisponibilidadeCpfPort disponibilidadeCpfPort;
    private final DisponibilidadeEmailPort disponibilidadeEmailPort;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    public AtualizarUsuarioService(UsuarioRepositoryPort usuarioRepository,
                                   DisponibilidadeCpfPort disponibilidadeCpfPort,
                                   DisponibilidadeEmailPort disponibilidadeEmailPort,
                                   BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase) {
        this.usuarioRepository = usuarioRepository;
        this.disponibilidadeCpfPort = disponibilidadeCpfPort;
        this.disponibilidadeEmailPort = disponibilidadeEmailPort;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
    }

    @Override
    public Usuario atualizar(Usuario usuario) {
        Usuario usuarioAtual = buscarUsuarioPorIdUseCase.buscarPorId(usuario.getId());
        if (!usuario.getCpf().equals(usuarioAtual.getCpf())) {
            validarDisponibilidadeCpf(usuario.getCpf());
        }

        if (!usuario.getCpf().equals(usuarioAtual.getCpf())) {
            validarDisponibilidadeEmail(usuario.getEmail());
        }

        return usuarioRepository.atualizar(usuario);
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
