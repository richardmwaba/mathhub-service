package com.hubformath.mathhubservice.jwt_auth;

import com.hubformath.mathhubservice.model.auth.UserAuthDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${mathhub.app.jwtSecret}")
    private String jwtSecret;

    @Value("${mathhub.app.jwtExpirationSecs}")
    private long jwtExpirationSecs;

    public String generateJwtAccessToken(Authentication authentication) {
        UserAuthDetails userPrincipal = (UserAuthDetails) authentication.getPrincipal();
        return generateJwtAccessToken(userPrincipal.getUsername());
    }

    public String generateJwtAccessToken(String username) {
        Date now = new Date();

        return Jwts.builder()
                   .subject(username)
                   .issuedAt(now)
                   .expiration(new Date(now.getTime() + (jwtExpirationSecs * 1000)))
                   .signWith(key())
                   .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtAccessToken(String token) {
        return Jwts.parser()
                   .verifyWith(key())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

    public boolean validateJwtAccessToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
