package com.hidiscuss.backend.controller.dto;


import com.hidiscuss.backend.entity.User;
import lombok.Builder;

@Builder
public class UserRankResponseDto {

    public  String username;
    public  Long point;

    public static UserRankResponseDto toEntity(User user) {
        return UserRankResponseDto.builder()
                .username(user.getName())
                .point(user.getPoint())
                .build();
    }
}
