package com.hidiscuss.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.entity.Token;
import com.hidiscuss.backend.service.TokenService;
import com.hidiscuss.backend.controller.dto.UserRequestMapper;
import com.hidiscuss.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHEmail;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final UserRepository userRepository;

    @Value("${environments.url.homeurl}")
    private String homeurl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        String gitaccessToken = oAuth2User.getAttribute("accessToken");
        // 최초 로그인이라면 회원가입 처리를 한다.
        User newUser = userRepository.findByName(oAuth2User.getName());
        if (newUser == null) {
            GitHub gitHub = GitHub.connectUsingOAuth(gitaccessToken);
            List<GHEmail>  ghEmails2 = gitHub.getMyself().getEmails2();
            User user = userRequestMapper.toUser(oAuth2User, gitaccessToken, ghEmails2.get(ghEmails2.size()- 1).getEmail());
            userRepository.save(user);
            newUser = user;
        }
        Token token = tokenService.generateToken(oAuth2User.getName(),gitaccessToken, String.valueOf(newUser.getId()));

        Cookie accessToken = new Cookie("accessToken", token.getToken());
        Cookie refreshToken = new Cookie("refreshToken", token.getRefreshToken());

        accessToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
        refreshToken.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        getRedirectStrategy().sendRedirect(request, response, homeurl);
    }
}