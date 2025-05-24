package com.v1.apiDebt.Io.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AlterarFotoRequest(@NotNull UUID idUsuario, @NotNull String fotoPerfilBase64) {
}
