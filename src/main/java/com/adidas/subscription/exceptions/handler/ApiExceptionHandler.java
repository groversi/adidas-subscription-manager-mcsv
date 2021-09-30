package com.adidas.subscription.exceptions.handler;

import com.adidas.subscription.exceptions.customs.*;
import com.adidas.subscription.exceptions.dto.ApiErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> errorManager(InvalidRequestException ex){
        return buildResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<?> errorManager(ParseException ex){
        return buildResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> errorManager(EntityNotFoundException ex){
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> errorManager(RuntimeException ex){
        log.error(ex.getMessage(), ex);
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> errorManager(InternalServerErrorException ex){
        log.error(ex.getMessage(), ex);
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ApiErrorResponseDTO> HandleMethodNotAllowedException(MethodNotAllowedException error) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getGatewayErrorDTO());
    }

    @ExceptionHandler(MethodForbidenException.class)
    public ResponseEntity<ApiErrorResponseDTO> HandleMethodForbidenException(MethodForbidenException error) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error.getGatewayErrorDTO());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponseDTO> HandleBadRequestException(BadRequestException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getGatewayError());
    }

    protected ResponseEntity<ApiErrorResponseDTO> buildResponse(RuntimeException ex, HttpStatus status){
        return new ResponseEntity<ApiErrorResponseDTO>(
                new ApiErrorResponseDTO(
                        status,
                        status.getReasonPhrase(),
                        ex.getMessage()
                ), status);
    }
}
