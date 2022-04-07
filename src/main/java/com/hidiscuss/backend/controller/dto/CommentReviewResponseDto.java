package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;
import com.hidiscuss.backend.entity.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
public class CommentReviewResponseDto extends BaseResponseDto {

    private Long id;

//    private User reviewer;

//    private Discussion discussion;

    private Boolean accepted;

    private ReviewType reviewType;

    public static CommentReviewResponseDto fromEntity(Review entity) {
        CommentReviewResponseDto dto = new CommentReviewResponseDto();
        dto.id = entity.getId();
//        dto.reviewer = entity.getReviewer();
//        dto.discussion = entity.getDiscussion();
        dto.accepted = entity.getAccepted();
        dto.reviewType = entity.getReviewType();
        return dto;
    }
}
