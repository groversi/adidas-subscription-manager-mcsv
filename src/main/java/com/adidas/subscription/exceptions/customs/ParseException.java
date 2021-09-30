package com.adidas.subscription.exceptions.customs;

public class ParseException extends RuntimeException{

    private static final long serialVersionUID = 9125277018717732648L;

    public ParseException(String message, Throwable throwable){super(message,throwable);}
}
