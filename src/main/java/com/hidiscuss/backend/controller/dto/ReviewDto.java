package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;

import java.util.List;


public class ReviewDto extends BaseResponseDto {
    private Long id;
    private UserResponseDto reviewer;
    private DiscussionResponseDto discussion;
    private List<ReviewDiffDto> diffList;
    private Boolean accepted;
    private ReviewType reviewType;

    public static ReviewDto fromEntity(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.id = review.getId();
        dto.reviewer = UserResponseDto.fromEntity(review.getReviewer());
        dto.discussion = DiscussionResponseDto.fromEntity(review.getDiscussion());
        dto.accepted = review.getAccepted();
        dto.reviewType = review.getReviewType();
        if (dto.reviewType == ReviewType.COMMENT) {
            dto.diffList = ReviewDiffDto.fromEntity(review.getCommentDiffList());
        } else if (dto.reviewType == ReviewType.LIVE) {
            dto.diffList = ReviewDiffDto.fromEntity(review.getLiveDiffList());
        }
        return dto;
    }
}
