package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.User;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public class UserResponseDto {
    public Long id;

    public String name;

    public String email;

    public Long point;

    public String accessToken;

    public static UserResponseDto fromEntity(User entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .point(entity.getPoint())
                .build();
    }
}
