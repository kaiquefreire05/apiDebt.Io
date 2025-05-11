package com.v1.apiDebt.Io.infra.adapter;

import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeCpfPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeEmailPort;
import com.v1.apiDebt.Io.infra.jpaRepository.UsuarioJpaRepository;

public class DisponibilidadesUsuarioAdapter implements DisponibilidadeEmailPort, DisponibilidadeCpfPort {
    // Injeção de dependência
    private final UsuarioJpaRepository repository;

    public DisponibilidadesUsuarioAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean cpfJaCadastrado(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public boolean emailJaCadastrado(String email) {
        return repository.existsByEmail(email);
    }
}
