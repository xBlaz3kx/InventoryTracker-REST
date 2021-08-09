package com.bdular.inventorytracker.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private final String secret = "secret";

    public boolean validateTokenForUser(String token, UserDetails user) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.getSubject().equals(user.getUsername()) && claims.getExpiration().before(new Date());
        } catch (JwtException | ClassCastException e) {
            return false;
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @return the JWT token
     */
    public String generateToken(UserDetails details) {

        return Jwts.builder()
                .setSubject(details.getUsername())
                .signWith(SignatureAlgorithm.HS256, secret)
                .setIssuedAt(new Date())
                .compact();
    }
}
