package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionTag;
import com.hidiscuss.backend.entity.Tag;
import lombok.Builder;

@Builder
public class UserRankResponseDto {

    public  String username;
    public  Long point;

    public static UserRankResponseDto toEntity(String username, Long point) {
        return UserRankResponseDto.builder()
                .username(username)
                .point(point)
                .build();
    }
}
