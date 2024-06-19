package com.dndbackendlayer.dndbackend.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.security.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;


import org.springframework.stereotype.Component;


@Component
public class JwtServices {

    private static final String SECRET = "yourNewLongerSecretKeyHereMakeSureItIsAtLeast32BytesLongAndSecure";

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); 
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
