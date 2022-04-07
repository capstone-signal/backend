package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewThread;
import com.hidiscuss.backend.entity.User;
import lombok.Getter;

@Getter
public class ThreadResponseDto extends BaseResponseDto {
    private Long id;

    //private User user;

    //private Review review;

    //private String content;

    public static ThreadResponseDto fromEntity(ReviewThread entity) {
        ThreadResponseDto dto = new ThreadResponseDto();
        dto.setBaseResponse(entity);
        dto.id = entity.getId();
//        dto.user = entity.getUser();
//        dto.review = entity.getReview();
//        dto.content = entity.getContent();
        return dto;
    }
}
