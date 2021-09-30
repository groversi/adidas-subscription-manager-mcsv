package com.adidas.subscription.exceptions.customs;


import com.adidas.subscription.exceptions.dto.ApiErrorResponseDTO;

public class BadRequestException extends RuntimeException {
    private ApiErrorResponseDTO apiErrorResponseDTO;

    public BadRequestException(ApiErrorResponseDTO gatewayErrorDTO){
        this.apiErrorResponseDTO = gatewayErrorDTO;
    }

    public ApiErrorResponseDTO getGatewayError() {
        return apiErrorResponseDTO;
    }
}
