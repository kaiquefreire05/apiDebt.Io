package com.v1.apiDebt.Io.infra.jpaRepository;

import com.v1.apiDebt.Io.infra.entity.ContasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ContasJpaRepository extends JpaRepository<ContasEntity, Long> {

}
