package org.imaginemos.authservice.util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key secretKey;
    private final long EXPIRATION_TIME = 1000 * 600 * 600;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes();
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);


//        System.out.println(" CLAVE SECRETA PARA EL GATEWAY:");
//        System.out.println(getSecretAsBase64());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String getSecretAsBase64() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}