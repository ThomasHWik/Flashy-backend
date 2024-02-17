package com.flashy.server.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTService {

    private final Algorithm algorithm;

    private final JWTVerifier verifier;

    private static final String ISSUER_NAME = "flashy";

    private static final int TOKEN_LIFESPAN_HOURS = 3;

    public JWTService(@Value("${JWTKEY}") String JWTKEY) {
        this.algorithm = Algorithm.HMAC256(JWTKEY);
        this.verifier = JWT.require(algorithm)
                .withIssuer(ISSUER_NAME)
                .build();
    }

    public String getToken(final String username) {
        long expirationTime = System.currentTimeMillis() + (1000 * 60 * 60 * TOKEN_LIFESPAN_HOURS);
        Date expirationDate = new Date(expirationTime);

        return JWT.create()
                .withIssuer(ISSUER_NAME)
                .withClaim("username", username)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String getUsernameFromToken(final String token) throws JWTVerificationException {
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public boolean checkUsernameCorrespondsWithToken(String username, String token) {

        return getUsernameFromToken(token).equals(username);
    }
}

