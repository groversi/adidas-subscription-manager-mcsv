package com.adidas.subscription.exceptions.customs;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
