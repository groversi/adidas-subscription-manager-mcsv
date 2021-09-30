package com.adidas.subscription.exceptions;

import com.adidas.subscription.exceptions.customs.*;
import com.adidas.subscription.exceptions.dto.ApiErrorResponseDTO;
import com.google.gson.Gson;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;


public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        ApiErrorResponseDTO apiErrorResponseDTO = null;
        try {
            apiErrorResponseDTO = new Gson().fromJson(IOUtils.toString(response.body().asInputStream(), Charset.defaultCharset().toString()), ApiErrorResponseDTO.class);
        } catch (Exception e) {
            log.error("could not deserialize response body", e);
        }
        log.error("Feign error decoder: status - {}", response.status());

        switch (response.status()){
            case 500:
                throw new InternalServerErrorException(response.reason());
            case 404:
                throw new ResourceNotFoundException(response.reason());
            case 403:
                throw new MethodForbidenException(apiErrorResponseDTO);
            case 401:
                throw new MethodNotAllowedException(apiErrorResponseDTO);
            case 400:
                throw new BadRequestException(apiErrorResponseDTO);
            default:
                throw new InternalServerErrorException(response.reason());
        }
    }

}
