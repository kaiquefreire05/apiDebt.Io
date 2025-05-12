package com.v1.apiDebt.Io.web.exceptionHandler;

import com.v1.apiDebt.Io.exceptions.*;
import com.v1.apiDebt.Io.web.dto.response.BaseResponse;
import com.v1.apiDebt.Io.web.dto.response.ErrorResponse;
import com.v1.apiDebt.Io.web.dto.response.ValidationError;
import com.v1.apiDebt.Io.infra.adapter.EntradaLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final EntradaLogService entradaLogService;

    public GlobalExceptionHandler(EntradaLogService entradaLogService) {
        this.entradaLogService = entradaLogService;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de validação nos campos.",
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                validationErrors
        );

        BaseResponse<Object> response = BaseResponse.<Object>builder()
                .success(false)
                .message("Campos inválidos.")
                .result(null)
                .error(errorResponse)
                .build();

        // Log a classe de origem da exceção
        String classeOrigem = obterClasseDeOrigem(ex);
        entradaLogService.saveLog("ERROR", classeOrigem, "Erro de validação nos campos.", ex.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({
        CpfInvalidoException.class,
        EmailInvalidoException.class,
        SenhaInvalidaException.class,
        TelefoneInvalidoException.class
    })
    public ResponseEntity<BaseResponse<Object>> handleCustomValidation(RuntimeException ex) {
        ValidationError error = new ValidationError("dado", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                List.of(error)
        );

        BaseResponse<Object> response = BaseResponse.<Object>builder()
                .success(false)
                .message(ex.getMessage())
                .result(null)
                .error(errorResponse)
                .build();

        // Log a classe de origem da exceção
        String classeOrigem = obterClasseDeOrigem(ex);
        entradaLogService.saveLog("ERROR", classeOrigem, ex.getMessage(), ex.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Erro interno no servidor.",
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                List.of(new ValidationError("erro", ex.getMessage()))
        );

        BaseResponse<Object> response = BaseResponse.<Object>builder()
                .success(false)
                .message("Erro inesperado.")
                .result(null)
                .error(errorResponse)
                .build();

        // Log a classe de origem da exceção
        String classeOrigem = obterClasseDeOrigem(ex);
        entradaLogService.saveLog("ERROR", classeOrigem, "Erro inesperado.", ex.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private String obterClasseDeOrigem(Throwable ex) {
        // Percorre o stack trace para encontrar a classe exata
        for (StackTraceElement element : ex.getStackTrace()) {
            String className = element.getClassName();

            // Ignora classes do Spring, Java e o próprio Handler
            if (!className.startsWith("java.") &&
                !className.startsWith("jakarta.") &&
                !className.startsWith("org.springframework") &&
                !className.startsWith(this.getClass().getPackageName())) {

                // Retorna a classe de origem
                return className.substring(className.lastIndexOf('.') + 1);
            }
        }
        return "ClasseDesconhecida";
    }
}
