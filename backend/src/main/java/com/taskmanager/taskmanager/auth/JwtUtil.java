package com.taskmanager.taskmanager.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey";

    // Generate JWT Token
    public static String generateToken(String email) {

        return Jwts.builder()

                .setSubject(email)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
                )

                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY.getBytes()
                )

                .compact();
    }

    // Extract Email
    public static String extractEmail(String token) {

        Claims claims = Jwts.parser()

                .setSigningKey(SECRET_KEY.getBytes())

                .parseClaimsJws(token)

                .getBody();

        return claims.getSubject();
    }
}