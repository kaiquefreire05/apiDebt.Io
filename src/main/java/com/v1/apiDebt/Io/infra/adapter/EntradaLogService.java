package com.v1.apiDebt.Io.infra.adapter;

import com.v1.apiDebt.Io.infra.entity.EntradaLogEntity;
import com.v1.apiDebt.Io.infra.jpaRepository.LogJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntradaLogService {
    // Injeção de dependência
    private final LogJpaRepository repository;

    public EntradaLogService(LogJpaRepository repository) {
        this.repository = repository;
    }

    public void saveLog(String level, String logger, String message, String exception) {
        EntradaLogEntity log = new EntradaLogEntity();
        log.setLogDate(LocalDateTime.now());
        log.setLevel(level);
        log.setLogger(logger);
        log.setMessage(message);
        log.setException(exception);
        repository.save(log);
    }
}
