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
    private String attributeKey;
    private String email;
    private String name;

    static OAuth2Attribute ofGitHub(
                                    Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("login"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .build();
    }


    Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("login", name);

        return map;
    }
}