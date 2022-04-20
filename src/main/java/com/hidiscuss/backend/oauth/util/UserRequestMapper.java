package com.hidiscuss.backend.oauth.util;

import com.hidiscuss.backend.entity.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public User toUser(OAuth2User oAuth2User, String  access_token) {
        var attributes = oAuth2User.getAttributes();

        return User.builder()
                .name((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .accessToken(access_token)
                .build();
    }
}