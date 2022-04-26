package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.entity.Token;
import com.hidiscuss.backend.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] authorizationCookie = request.getCookies();
        String AccessToken = null;
        String Refreshtoken = null;

        for(int i = 0; i < authorizationCookie.length; i++){
            if(authorizationCookie[i].getName().equals("accessToken")){
                AccessToken = authorizationCookie[i].getValue();
            }
            else if(authorizationCookie[i].getName().equals("refreshToken")){
                Refreshtoken = authorizationCookie[i].getValue();
            }
        }
        Claims claims = tokenService.parseJwtToken(AccessToken);

        if (Refreshtoken != null && tokenService.verifyToken(Refreshtoken)) {
            String name = tokenService.getUid(Refreshtoken);
            Token newToken = tokenService.generateToken(name, (String) claims.get("gitAccessToken"));

            Cookie accessToken = new Cookie("accessToken", newToken.getToken());
            Cookie refreshToken = new Cookie("refreshToken", newToken.getRefreshToken());

            accessToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
            refreshToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정

            response.addCookie(accessToken);
            response.addCookie(refreshToken);

            return "HAPPY NEW TOKEN";
        }

        throw new RuntimeException();
    }

    @GetMapping("/token/fail")
    public String failLogin(HttpServletResponse response, HttpServletRequest request){
        return "FailLogin";
    }
}