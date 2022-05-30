package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.Token;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class TokenService {

    @Value("${environments.key.secretKey}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token generateToken(String uid, String gitAccessToken, String userId) {
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
                        .claim("userId",userId.toString())
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .claim("gitAccessToken",gitAccessToken)
                        .claim("userId", userId.toString())
                        .compact());
    }
    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
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