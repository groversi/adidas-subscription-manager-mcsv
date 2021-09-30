package com.adidas.subscription.exceptions.customs;


import com.adidas.subscription.exceptions.dto.ApiErrorResponseDTO;

public class MethodForbidenException extends RuntimeException {
    private ApiErrorResponseDTO apiErrorResponseDTO;

    public MethodForbidenException(ApiErrorResponseDTO gatewayErrorDTO){
        this.apiErrorResponseDTO = gatewayErrorDTO;
    }

    public ApiErrorResponseDTO getGatewayErrorDTO() {
        return apiErrorResponseDTO;
    }
}
