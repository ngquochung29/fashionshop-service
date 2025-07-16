package shop.server.webconfig.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/
@Component
public class JwtUtil {
    private final String ACCESS_SECRET = "access-secret-key-must-be-256bit...";

    private final long ACCESS_EXPIRATION_MS = 5 * 60 * 1000; // 5 phút
    private final long REFRESH_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000; // 7 ngày

    public String generateAccessToken(String username,String roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.split(","));
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_MS))
                .signWith(Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_MS))
                .signWith(Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(ACCESS_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

