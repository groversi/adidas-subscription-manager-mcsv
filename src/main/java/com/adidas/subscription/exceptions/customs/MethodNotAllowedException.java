package com.adidas.subscription.exceptions.customs;


import com.adidas.subscription.exceptions.dto.ApiErrorResponseDTO;

public class MethodNotAllowedException extends RuntimeException {
    private ApiErrorResponseDTO apiErrorResponseDTO;

    public MethodNotAllowedException(ApiErrorResponseDTO gatewayErrorDTO){
        this.apiErrorResponseDTO = gatewayErrorDTO;
    }

    public ApiErrorResponseDTO getGatewayErrorDTO() {
        return apiErrorResponseDTO;
    }
}
