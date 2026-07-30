package com.planatech.socialposter.facebook_media_service.config;

import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.security.oauth2.jwt.JwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtility {

    // Replace with the same secret used in your SecurityConfig
    private static final String SECRET = "8f9d3b2c1a7e4f5d9e3b2c1a7e4f5d9e3b2c1a7e4f5d9e3b2c1a7e4f5d9e3b2c1a";

    // Generate JWT token
    public static String generateToken(String subject, String role, long expirationMillis) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate and decode JWT token
    public static void validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("✅ Token is valid");
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Roles: " + claims.get("roles"));
            System.out.println("Expiration: " + claims.getExpiration());

        } catch (JwtException e) {
            System.out.println("❌ Invalid token: " + e.getMessage());
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Generate token valid for 1 hour
        String token = generateToken("aman", "USER", 60 * 60 * 1000);
        System.out.println("Generated JWT:\n" + token);

        // Validate token
        validateToken(token);
    }
}