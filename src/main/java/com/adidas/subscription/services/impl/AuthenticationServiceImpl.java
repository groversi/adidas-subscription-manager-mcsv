package com.adidas.subscription.services.impl;

import com.adidas.subscription.exceptions.customs.InternalServerErrorException;
import com.adidas.subscription.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private static final String regexKeys = "(-{3,}([\\s\\S]*?)-{3,})|(\\r\\n)|(\\R)";
    private static final String encryptStrategy = "RSA";
    private static final String issuerName = "subscriptionGateway";


    public Boolean validateJwtToken(String jwtToken){
        PublicKey publicKey = retrievePublicKey();
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwtToken.replaceAll("Bearer ", ""));
        }catch (RuntimeException ex){
            log.error(ex.getMessage(), ex);
            return false;
        }

        return jws.getBody().getIssuer().equals(issuerName)
                && jws.getBody().getExpiration().compareTo(new Date()) > 0
                && jws.getBody().getNotBefore().compareTo(new Date()) < 0;
    }

    private PublicKey retrievePublicKey(){
        try{
            InputStream file = new ClassPathResource("certs/public.pem").getInputStream();
            String publicKeyString = new Scanner(file, StandardCharsets.UTF_8.name()).useDelimiter("\\A").next().replaceAll(regexKeys, "");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
            return KeyFactory.getInstance(encryptStrategy).generatePublic(keySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
