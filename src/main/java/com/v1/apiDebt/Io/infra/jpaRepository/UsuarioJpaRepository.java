package com.v1.apiDebt.Io.infra.jpaRepository;

import com.v1.apiDebt.Io.infra.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<UsuarioEntity> findByEmail(String email);
}
