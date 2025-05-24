package com.v1.apiDebt.Io.infra.adapter;

import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeCpfPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeEmailPort;
import com.v1.apiDebt.Io.application.ports.output.disponibilidade.DisponibilidadeGastoPort;
import com.v1.apiDebt.Io.infra.entity.ContasEntity;
import com.v1.apiDebt.Io.infra.entity.UsuarioEntity;
import com.v1.apiDebt.Io.infra.jpaRepository.ContasJpaRepository;
import com.v1.apiDebt.Io.infra.jpaRepository.UsuarioJpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

public class DisponibilidadesUsuarioAdapter implements DisponibilidadeEmailPort,
        DisponibilidadeCpfPort,
        DisponibilidadeGastoPort {
    // Injeção de dependência
    private final UsuarioJpaRepository repository;
    private final ContasJpaRepository contasRepository;

    public DisponibilidadesUsuarioAdapter(UsuarioJpaRepository repository,
                                          ContasJpaRepository contasRepository) {
        this.repository = repository;
        this.contasRepository = contasRepository;
    }

    @Override
    public boolean cpfJaCadastrado(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public boolean emailJaCadastrado(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean verificarDisponibilidadeGasto(UUID id, BigDecimal valorAlvo) {
        UsuarioEntity usuario = repository.findById(id).orElse(null);
        if (usuario == null) {
            return false;
        }

        List<ContasEntity> contas = contasRepository.findAllByUsuarioId(id);
        if (contas.isEmpty()) {
            return false;
        }

        LocalDate hoje = LocalDate.now();
        BigDecimal totalGastos = BigDecimal.ZERO;

        for (ContasEntity conta : contas) {
            LocalDate dataVencimento = conta.getDataVencimento();
            if (dataVencimento.getMonth() == hoje.getMonth() &&
                dataVencimento.getYear() == hoje.getYear()) {
                totalGastos = totalGastos.add(conta.getValor());
            }
        }

        BigDecimal valorMaximoGasto = usuario.getRendaMensal().multiply(usuario.getPercentualGastos());
        return totalGastos.add(valorAlvo).compareTo(valorMaximoGasto) <= 0;
    }


}
