package com.v1.apiDebt.Io.web.factory;

import com.v1.apiDebt.Io.web.dto.response.BaseResponse;
import com.v1.apiDebt.Io.web.dto.response.ErrorResponse;

public class BaseResponseFactory {
    /**
     * Essa classe é responsável por criar instâncias de BaseResponse.
     * Ela utiliza o padrão de projeto Factory para encapsular a lógica de criação de objetos.
     */
    public static <T> BaseResponse<T> sucesso(T result, String message) {
        return BaseResponse.<T>builder()
                .success(true)
                .result(result)
                .message(message)
                .error(null)
                .build();
    }

    public static <T> BaseResponse<T> falha(String message) {
        return BaseResponse.<T>builder()
                .success(false)
                .result(null)
                .message(message)
                .error(null)
                .build();
    }

    public static <T> BaseResponse<T> falhaGlobal(String message, ErrorResponse errorResponse) {
        return BaseResponse.<T>builder()
                .success(false)
                .result(null)
                .message(message)
                .error(null)
                .build();
    }
}

