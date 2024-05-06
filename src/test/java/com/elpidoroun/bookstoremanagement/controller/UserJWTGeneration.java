package com.elpidoroun.bookstoremanagement.controller;

import com.elpidoroun.bookstoremanagement.security.user.Permissions;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserJWTGeneration {

    public static String generateAdminToken(){
        return generateToken("test_admin@gmail.com");
    }

    public static String generateUserToken(){
        return generateToken("test_user@gmail.com");
    }


    private static String generateToken(String user) {
        long expirationTimeInMillis = 3600000; // 1 hour

        List<String> allPermissions = Stream.of(Permissions.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("permissions", allPermissions);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey() {
        String secretKey = "XH/xVXaDOovKM+Do/U+fVN6hefbrsNo3PjK15vmdDnha9Jt7+a4pBhC5ZrmGt5K/kRzzI2+herjexcORZGvO00Y4rSYQxwYKG5UAjMsbT9wkAJKfuoPqokmFAlKgtQIv9MVtSmNRlBzOxDMkonJKBzI+Uw3qo1je1BhIPUrOaG+T0UpNjEsxDKN7pWPi+cTp/I3iXbLdDb/2KgcPvUvxULXIKnu4oPOVWxRowMSKn2RIkw40+xuJsJYsc54wbKAIkCb//ji8Uwc2+mWZVF5Nk3GhxVRkwtvSKuC5JareWsiGKkmYKN09X0utK/wA4zIm55JAeL7HplRUi9JDdrRGZlH/1bm+kPYOZkiH0dw3ssI=";

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
