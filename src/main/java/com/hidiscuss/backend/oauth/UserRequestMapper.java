package com.hidiscuss.backend.oauth;

import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.oauth.UserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public User toUser(OAuth2User oAuth2User, Token token) {
        var attributes = oAuth2User.getAttributes();

        return User.builder()
                .username((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .accessToken(token.getToken())
                .refresh_token(token.getRefreshToken())
                .build();
    }
}