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

    static OAuth2Attribute ofGitHub(String provider, String attributeKey,
                                    Map<String, Object> attributes) {
        System.out.println("++++++++++=+++++++++" + attributeKey+ attributes.get("login"));
        return OAuth2Attribute.builder()
                .name((String) attributes.get("login"))
                .attributes(attributes)
                .attributeKey(attributeKey)
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