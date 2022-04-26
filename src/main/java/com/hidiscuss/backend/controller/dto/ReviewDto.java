package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;

import java.util.ArrayList;
import java.util.List;


public class ReviewDto extends BaseResponseDto {
    private Long id;
    private UserResponseDto reviewer;
    private DiscussionResponseDto discussion;
    private List<CommentReviewDiffDto> commentDiffList = new ArrayList<>();
    private List<LiveReviewDiffDto> liveDiffList = new ArrayList<>();
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
            List<CommentReviewDiff> entityList = review.getCommentDiffList();
            for (CommentReviewDiff item : entityList)
                dto.commentDiffList.add(CommentReviewDiffDto.fromEntity(item));
        } else if (dto.reviewType == ReviewType.LIVE) {
            List<LiveReviewDiff> entityList = review.getLiveDiffList();
            for (LiveReviewDiff item : entityList)
                dto.liveDiffList.add(LiveReviewDiffDto.fromEntity(item));
        }
        return dto;
    }
}
