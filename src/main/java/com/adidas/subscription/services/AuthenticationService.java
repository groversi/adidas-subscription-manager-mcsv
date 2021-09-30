package com.adidas.subscription.services;

public interface AuthenticationService {

    Boolean validateJwtToken(String jwtToken);
}
