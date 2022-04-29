package com.hidiscuss.backend.Oauth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public
class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String email;
    private String name;
    private String accessToken;

    public static OAuth2Attribute ofGitHub(
            Map<String, Object> attributes, String accessToken) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("login"))
                .accessToken(accessToken)
                .attributes(attributes)
                .build();
    }


    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("login", name);

        return map;
    }
}