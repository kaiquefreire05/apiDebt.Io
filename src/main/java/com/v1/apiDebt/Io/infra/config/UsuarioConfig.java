package com.v1.apiDebt.Io.infra.config;

import com.v1.apiDebt.Io.application.ports.input.usuario.*;
import com.v1.apiDebt.Io.application.ports.output.UsuarioRepositoryPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeCpfPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeEmailPort;
import com.v1.apiDebt.Io.application.services.usuarios.*;
import com.v1.apiDebt.Io.infra.adapter.DisponibilidadesUsuarioAdapter;
import com.v1.apiDebt.Io.infra.jpaRepository.UsuarioJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new BuscarUsuarioPorEmailService(usuarioRepositoryPort);
    }

    @Bean
    public AutenticarUsuarioUseCase autenticarUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new AutenticarUsuarioService(usuarioRepositoryPort);
    }

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(UsuarioRepositoryPort usuarioRepository,
                                                   DisponibilidadeCpfPort disponibilidadeCpfPort,
                                                   DisponibilidadeEmailPort disponibilidadeEmailPort) {
        return new CriarUsuarioService(usuarioRepository, disponibilidadeCpfPort, disponibilidadeEmailPort);
    }

    @Bean
    public DisponibilidadeCpfPort disponibilidadeCpfPort(UsuarioJpaRepository repository) {
        return new DisponibilidadesUsuarioAdapter(repository);
    }

    @Bean
    public DisponibilidadeEmailPort disponibilidadeEmailPort(UsuarioJpaRepository repository) {
        return new DisponibilidadesUsuarioAdapter(repository);
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase(UsuarioRepositoryPort usuarioRepository,
                                   DisponibilidadeCpfPort disponibilidadeCpfPort,
                                   DisponibilidadeEmailPort disponibilidadeEmailPort) {
        return new AtualizarUsuarioService(usuarioRepository, disponibilidadeCpfPort, disponibilidadeEmailPort);
    }

    @Bean
    public DeletarUsuarioUseCase deletarUsuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new DeletarUsuarioService(usuarioRepositoryPort);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new ListarUsuariosService(usuarioRepositoryPort);
    }

    @Bean
    public ConfirmarCadastroUseCase confirmarCadastroUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new ConfirmarCadastroService(usuarioRepositoryPort);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new BuscarUsuarioPorIdService(usuarioRepositoryPort);
    }

    @Bean
    public AlterarSenhaUseCase alterarSenhaUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new AlterarSenhaService(usuarioRepositoryPort);
    }

}
