package shop.server.webconfig.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.server.webconfig.security.JwtUtil;
import shop.server.webconfig.security.UserDT;

@Slf4j
public class AuthTokenFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = parseJwt(httpServletRequest);
        if (StringUtils.hasText(jwt)) {
            try {
//                this.tokenProvider.parseToken(jwt);
                Authentication authentication = this.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException ex) {
                log.error("Method: doFilter, jwt expired : ", ex);
                return;
            } catch (Exception ex) {
                log.error("Method: doFilter, exception : ", ex);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {

        Claims claims = JwtUtil.extractAllClaims(token);

        String username = claims.getSubject();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        ((List<String>) claims.get("roles")).forEach(
                r -> authorities.add(new SimpleGrantedAuthority(r)));

        User principal = new User(username, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}