package com.v1.apiDebt.Io.application.ports.input.contas;

import com.v1.apiDebt.Io.domain.enums.StatusContaEnum;
import com.v1.apiDebt.Io.domain.models.Contas;

public interface AlterarStatusContaUseCase {
    Contas alterarStatusConta(Long contaId, StatusContaEnum status);
}
