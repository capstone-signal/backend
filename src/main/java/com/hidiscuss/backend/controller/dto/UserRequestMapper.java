package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public User toUser(OAuth2User oAuth2User, String  access_token, String user_email) {
        var attributes = oAuth2User.getAttributes();

        return User.builder()
                .name((String) attributes.get("login"))
                .email(user_email)
                .accessToken(access_token)
                .build();
    }
}