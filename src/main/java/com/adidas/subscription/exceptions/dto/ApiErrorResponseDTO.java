package com.adidas.subscription.exceptions.dto;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ApiErrorResponseDTO {
    private final String timestamp = Instant.now().toString();
    private final Integer status;
    private final String error;
    private final String message;
    private final String traceId = MDC.get("traceId");

    public ApiErrorResponseDTO(HttpStatus status,
                               String error,
                               String message){
        this.status = status.value();
        this.error = error;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getTraceId() {
        return traceId;
    }
}
