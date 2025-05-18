package com.v1.apiDebt.Io.infra.jpaRepository;

import com.v1.apiDebt.Io.domain.enums.CategoriasEnum;
import com.v1.apiDebt.Io.infra.entity.ContasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContasJpaRepository extends JpaRepository<ContasEntity, Long> {

    List<ContasEntity> findAllByCodigoRecorrencia(Long codigoRecorrencia);
    @Query("SELECT c FROM ContasEntity c WHERE c.codigoRecorrencia = ?1 AND c.usuario.id = ?2")
    List<ContasEntity> findAllByCodigoRecorrenciaAndCodigoUsuario(Long codigoRecorrencia, UUID codigoUsuario);
    @Query("SELECT c FROM ContasEntity c WHERE c.usuario.id = ?1")
    List<ContasEntity> findAllByUsuarioId(UUID usuarioId);
    @Query("SELECT c FROM ContasEntity c WHERE c.nomeCompra like ?1")
    Optional<ContasEntity> findByNomeCompra(String nomeCompra);
    Optional<ContasEntity> findByCategoria(CategoriasEnum categoria);
    Optional<ContasEntity> findByDataVencimento(LocalDate dataVencimento);
    Optional<ContasEntity> findByDataPagamento(LocalDateTime dataPagamento);
    @Query("SELECT c FROM ContasEntity c WHERE c.usuario.id = ?1")
    Optional<ContasEntity> findbyUsuarioId(UUID id);
}
