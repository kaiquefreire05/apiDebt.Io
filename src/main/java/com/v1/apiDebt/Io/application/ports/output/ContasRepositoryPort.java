package com.v1.apiDebt.Io.application.ports.output;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.domain.enums.StatusContaEnum;
import com.v1.apiDebt.Io.domain.models.Contas;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContasRepositoryPort {
    List<Contas> criar(Contas contas);
    boolean deletarConta(Long contaId);
    Contas atualizar(Contas contas);
    List<Contas> listarContasPorUsuario(UUID usuarioId);
    Optional<Contas> buscarPorId(Long id);
    Optional<Contas> buscarPorNome(String nome);
    Optional<Contas> buscarPorCategoria(CategoriasEnum categoriasEnum);
    Optional<Contas> buscarPorDataVencimento(String dataVencimento);
    Optional<Contas> buscarPorDataPagamento(String dataPagamento);
    Optional<Contas> buscarPorUsuarioId(UUID id);
    BigDecimal obterTotalGastoMes(UUID usuarioId);
    BigDecimal gastoDisponivel(UUID usuarioId);
    Contas alterarStatus(Long contaId, StatusContaEnum status);
}
