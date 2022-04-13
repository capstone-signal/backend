package com.hidiscuss.backend.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController

public class AuthController {

    @GetMapping("/auth/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("refresh", "1");
        response.setHeader("auth", "1");
        System.out.printf("============"+response.getHeader("refresh")
                + response.getHeader("auth"));
    }
}
