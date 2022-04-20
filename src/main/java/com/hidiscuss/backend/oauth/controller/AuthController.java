package com.hidiscuss.backend.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController

public class AuthController {

    @GetMapping("/auth/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("refresh", null);
        response.setHeader("auth", null);
        response.setHeader("Authorization", null);
        return "logouted";
    }
}
