package com.pricing.services.exceptions.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    @Schema(description = "ErrorCode", example = "400 | 404 | 500")
    private String errorCode;
    @Schema(description = "mesage", example = "Error description")
    private String message;
    @Schema(description = "mesage", example = "2024-12-31-23.59.59")
    private LocalDateTime timestamp;

    // Constructor con todos los parámetros
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }


}
