package com.adidas.subscription.exceptions.customs;


public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String errorMessage){
        super(errorMessage);
    }
}
