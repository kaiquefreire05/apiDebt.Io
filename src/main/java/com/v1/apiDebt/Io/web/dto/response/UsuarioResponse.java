package com.v1.apiDebt.Io.web.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String sobrenome,
        String email,
        String cpf,
        String telefone,
        LocalDate dataNascimento,
        BigDecimal rendaMensal,
        LocalDateTime dataCadastro
) {
}
