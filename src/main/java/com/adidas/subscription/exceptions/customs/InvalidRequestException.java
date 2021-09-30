package com.adidas.subscription.exceptions.customs;

public class InvalidRequestException extends RuntimeException{

    private static final long serialVersionUID = 9125277018717732648L;

    public InvalidRequestException(String message){super(message);}
}
