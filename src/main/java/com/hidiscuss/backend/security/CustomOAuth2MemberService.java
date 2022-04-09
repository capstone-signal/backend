package com.hidiscuss.backend.security;


import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService{

    private final UserRepository userRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        User user = saveOrUpdate(oAuth2User);

        session.setAttribute("oAuthToken", userRequest.getAccessToken().getTokenValue());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                oAuth2User.getAttributes(), "login");
    }

    public User saveOrUpdate(OAuth2User oAuth2User) {
        User oAuthUser = User.builder()
                .username(oAuth2User.getAttribute("login"))
                .name(oAuth2User.getAttribute("name"))
                .role(UserRole.USER)
                .build();

        User user = userRepository.findByUsername(oAuthUser.getUsername())
                .map(entity -> entity.update(oAuthUser))
                .orElse(oAuthUser);
        return userRepository.save(user);
    }
}
