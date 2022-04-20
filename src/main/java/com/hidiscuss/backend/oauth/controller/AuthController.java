package com.hidiscuss.backend.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AuthController  extends SavedRequestAwareAuthenticationSuccessHandler {

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie gitAccessToken = new Cookie("gitAccessToken", null);
        Cookie accessToken = new Cookie("accessToken", null);
        Cookie refreshToken = new Cookie("refreshToken", null);

        gitAccessToken.setMaxAge(0);
        accessToken.setMaxAge(0);
        refreshToken.setMaxAge(0);

        gitAccessToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
        accessToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
        refreshToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정

        response.addCookie(gitAccessToken);
        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        try {
            getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "logouted";
    }
}
