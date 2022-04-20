package com.hidiscuss.backend.oauth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String email;
    private String name;
    private String accessToken;

    static OAuth2Attribute ofGitHub(
                                    Map<String, Object> attributes, String accessToken) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .accessToken(accessToken)
                .attributes(attributes)
                .build();
    }


    Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("email",email);
        map.put("accessToken", accessToken);
        map.put("login", name);

        return map;
    }
}