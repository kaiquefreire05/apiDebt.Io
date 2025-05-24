package com.v1.apiDebt.Io.application.ports.output.disponibilidade;

import java.math.BigDecimal;
import java.util.UUID;

public interface DisponibilidadeGastoPort {
    boolean verificarDisponibilidadeGasto(UUID id, BigDecimal valorAlvo);
}
