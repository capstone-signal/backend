package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.User;
import lombok.Builder;

@Builder
public class UserResponseDto {
    private String name;

    private String email;

    private Long point;

    private String accessToken;

    public static UserResponseDto fromEntity(User entity) {
        return UserResponseDto.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .point(entity.getPoint())
                .accessToken(entity.getAccessToken())
                .build();
    }
}
