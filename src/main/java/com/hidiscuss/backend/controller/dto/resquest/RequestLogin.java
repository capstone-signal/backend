package com.hidiscuss.backend.controller.dto.resquest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestLogin {
    private String loginUrl;

    @Builder
    public RequestLogin(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
