package shop.server.webconfig.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import shop.server.exception.FashionException;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 16/7/2025,
 **/

public class JwtUtil {
    private static String ACCESS_SECRET = "c981e632-515c-4f2b-be09-07ca6bea0f8c";

    private static final long ACCESS_EXPIRATION_MS = 5 * 60 * 100000; // 5 phút
    private static final long REFRESH_EXPIRATION_MS = 7 * 24 * 60 * 60 * 1000; // 7 ngày

    public static String generateAccessToken(String username,String roles) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", username);
        payload.put("roles", roles.split(","));
        try {
            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setClaims(payload)
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_MS))
                    .signWith(Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes()), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_MS))
                .signWith(Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateToken(String token) {
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

    public static List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);

        // Lấy các quyền hạn từ claim "roles" hoặc "authorities" trong JWT
        List<String> roles = claims.get("roles", List.class); // Giả sử bạn lưu quyền hạn trong claim "roles"

        // Chuyển các quyền hạn thành List<GrantedAuthority>
        return roles.stream()
                .map(SimpleGrantedAuthority::new) // Chuyển mỗi role thành một SimpleGrantedAuthority
                .collect(Collectors.toList());
    }

    // Giải mã JWT và trích xuất tất cả claims
    public static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(ACCESS_SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public static <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public static String getUsername() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new FashionException(HttpStatus.UNAUTHORIZED,"Forbidden");
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

