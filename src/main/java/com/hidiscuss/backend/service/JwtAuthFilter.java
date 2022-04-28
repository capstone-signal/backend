package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.Token;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String token = null;
        String refreshToken = null;
        Cookie[] cookies = req.getCookies();
        for(int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("accessToken")){
                token = cookies[i].getValue();
            }else if(cookies[i].getName().equals("refreshToken")){
                refreshToken = cookies[i].getValue();
            }
        }
        Claims claims = tokenService.parseJwtToken(refreshToken);
        if (token != null && tokenService.verifyToken(token)) {
            String name = tokenService.getUid(token);

            Authentication auth = new UsernamePasswordAuthenticationToken(name,"");
            SecurityContextHolder.getContext().setAuthentication(auth);
        }else if (refreshToken != null && tokenService.verifyToken(refreshToken)) {
            String name = tokenService.getUid(refreshToken);
            Token newToken = tokenService.generateToken(name, (String) claims.get("gitAccessToken"));

            Cookie newAccessToken = new Cookie("accessToken", newToken.getToken());
            Cookie newRefreshToken = new Cookie("refreshToken", newToken.getRefreshToken());

            newAccessToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
            newRefreshToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정

            res.addCookie(newAccessToken);
            res.addCookie(newRefreshToken);
        }
    }
}