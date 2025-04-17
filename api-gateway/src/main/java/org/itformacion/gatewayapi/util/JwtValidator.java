package org.itformacion.gatewayapi.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtValidator {


    private String secretKey = "my-super-secret-key-auth-1234567890";


    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token expirado");
        } catch (JwtException e) {
            System.out.println("❌ Token inválido");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}