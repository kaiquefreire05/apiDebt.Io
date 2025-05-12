package com.v1.apiDebt.Io.web.dto.response;

import java.util.List;

public record ErrorResponse(String message, String code, List<ValidationError> validations) {
}
