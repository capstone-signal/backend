package com.hidiscuss.backend.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.UserRepository;
import com.hidiscuss.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        String accessToken = oAuth2User.getAttribute("accessToken");
        // 최초 로그인이라면 회원가입 처리를 한다.
        Token token = tokenService.generateToken(oAuth2User.getName());

        User user1 =userRepository.getByName(oAuth2User.getName());
        if (user1 == null) {
            User user = userRequestMapper.toUser(oAuth2User, accessToken);
            userRepository.save(user);
        }

        writeTokenResponse(response, token, accessToken);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token, String accessToken)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println(token.getToken()+ token.getRefreshToken());

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.addHeader("Authorization", "token " + accessToken);
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}