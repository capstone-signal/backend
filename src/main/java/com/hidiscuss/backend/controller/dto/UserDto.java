package com.hidiscuss.backend.controller.dto;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private String username;
    private String name;
    private String avatarUrl;
    private int followersCount;
    private String location;
    private Date createdAt;
    private Date updatedAt;

    @Builder
    public UserDto(String username, String name, String avatarUrl, int followersCount, String location, Date createdAt, Date updatedAt) {
        this.username = username;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.followersCount = followersCount;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
