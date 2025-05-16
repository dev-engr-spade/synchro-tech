package com.synchrotech.commandcenter.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${app.jwt.secret:defaultsecretkeydefaultsecretkey}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}") // 1 day
    private long jwtExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String userId, String username, String tenantId, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claims)
                .claim("username", username)
                .claim("tenantId", tenantId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    public String getUserId(String token) {
        return validateToken(token).getBody().getSubject();
    }
} 