package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    @Value("${environments.key.secretKey}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token generateToken(String uid, String gitAccessToken, Long userId) {
        long tokenPeriod = 1000L * 60L * 60L * 2L;
        long refreshPeriod = 1000L * 60L * 60L * 8L;

        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", "ROLE_USER");

        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .claim("gitAccessToken",gitAccessToken)
                        .claim("userId",userId)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .claim("gitAccessToken",gitAccessToken)
                        .claim("userId",userId)
                        .compact());
    }
    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Claims parseJwtToken(String authorizationHeader) {

        return Jwts.parser()
                .setSigningKey(secretKey) // (3)
                .parseClaimsJws(authorizationHeader) // (4)
                .getBody();
    }
}