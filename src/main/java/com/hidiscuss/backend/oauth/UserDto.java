package com.hidiscuss.backend.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String name;
    private String email;

    @Builder
    public UserDto(String email, String name) {
        this.name = name;
        this.email = email;
    }
}