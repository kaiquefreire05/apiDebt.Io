package com.v1.apiDebt.Io.infra.jpaRepository;

import com.v1.apiDebt.Io.infra.entity.EntradaLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogJpaRepository extends JpaRepository<EntradaLogEntity, Long> {
}
