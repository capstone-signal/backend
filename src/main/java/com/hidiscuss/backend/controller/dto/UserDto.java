package com.hidiscuss.backend.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String name;

    @Builder
    public UserDto(String email, String name, String access_token) {
        this.name = name;
    }
}