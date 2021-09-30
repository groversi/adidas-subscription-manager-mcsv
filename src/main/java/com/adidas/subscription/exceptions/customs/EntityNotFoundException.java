package com.adidas.subscription.exceptions.customs;

public class EntityNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 9125277018717732648L;

    public EntityNotFoundException(String message){super(message);}
}
