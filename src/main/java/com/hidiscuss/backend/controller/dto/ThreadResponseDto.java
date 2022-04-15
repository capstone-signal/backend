package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.ReviewThread;
import lombok.Getter;

@Getter
public class ThreadResponseDto extends BaseResponseDto {
    private Long id;

    private UserResponseDto user;

    private CommentReviewResponseDto review;

    private String content;

    public static ThreadResponseDto fromEntity(ReviewThread entity) {
        ThreadResponseDto dto = new ThreadResponseDto();
        dto.setBaseResponse(entity);
        dto.id = entity.getId();
        dto.user = UserResponseDto.fromEntity(entity.getUser());
        dto.review = CommentReviewResponseDto.fromEntity(entity.getReview());
        dto.content = entity.getContent();
        return dto;
    }
}
