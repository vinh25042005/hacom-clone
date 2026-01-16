package com.hacom.clone.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // Chìa khóa bí mật để mã hóa (Bạn nên để dài và phức tạp)
    private final String JWT_SECRET = "HacomCloneProjectSecretKey2026_SecureKey";
    private final long JWT_EXPIRATION = 604800000L; // Token có hạn trong 7 ngày

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}