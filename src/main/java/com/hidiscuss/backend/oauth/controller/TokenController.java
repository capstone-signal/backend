package com.hidiscuss.backend.oauth.controller;

import com.hidiscuss.backend.oauth.token.Token;
import com.hidiscuss.backend.oauth.token.TokenService;
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
        String token = null;
        Cookie[] authorizationCookie = request.getCookies();
        String AccessToken = null;
        for(int i = 0; i < authorizationCookie.length; i++){
            if(authorizationCookie[i].getName().equals("accessToken")){
                AccessToken = authorizationCookie[i].getValue();
            }
            else if(authorizationCookie[i].getName().equals("refreshToken")){
                token = authorizationCookie[i].getValue();
            }
        }
        Claims claims = tokenService.parseJwtToken(AccessToken);

        if (token != null && tokenService.verifyToken(token)) {
            String name = tokenService.getUid(token);
            Token newToken = tokenService.generateToken(name, (String) claims.get("gitAccessToken"));

//            //Cookie accessToken = new Cookie("accessToken", token.getToken());
//            //Cookie refreshToken = new Cookie("refreshToken", token.getRefreshToken());
//
//            accessToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
//            refreshToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
//
//            response.addCookie(accessToken);
//            response.addCookie(refreshToken);

            return "HAPPY NEW TOKEN";
        }

        throw new RuntimeException();
    }

    @GetMapping("/token/fail")
    public String failLogin(HttpServletResponse response, HttpServletRequest request){
        System.out.println("Aaa");
        return "AA";
    }
}