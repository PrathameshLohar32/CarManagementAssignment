package com.CarManagementApplication.CarManagementApplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Value("${spring.app.jwtExpirationMs}")
    private long EXPIRATION_TIME;

    @Value("${spring.app.jwtSecret}")
    private String JWT_SECRET;

    private SecretKey key;

    @PostConstruct
    public void init() {
        logger.debug("Initializing JWT secret key");
        byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails) {
        logger.debug("Generating token for user: {}", userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            String username = extractClaims(token, Claims::getSubject);
            logger.debug("Extracted username from token: {}", username);
            return username;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Error extracting username from token: {}", e.getMessage());
            return null;
        }
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsTFunction.apply(claims);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error parsing JWT: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean isValid = (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
            logger.debug("Token validation for user {}: {}", userDetails.getUsername(), isValid);
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expiration = extractClaims(token, Claims::getExpiration);
            boolean isExpired = expiration.before(new Date());
            logger.debug("Token expiration check: {} (expired: {})", expiration, isExpired);
            return isExpired;
        } catch (ExpiredJwtException e) {
            logger.debug("Token is already expired");
            return true;
        }
    }
}