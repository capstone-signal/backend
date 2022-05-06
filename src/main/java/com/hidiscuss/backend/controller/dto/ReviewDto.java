package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewDto extends BaseResponseDto {
    private Long id;
    private UserResponseDto reviewer;
    private List<CommentReviewDiffResponseDto> commentDiffList = new ArrayList<>();
    private List<LiveReviewDiffResponseDto> liveDiffList = new ArrayList<>();
    private List<ThreadResponseDto> threadList = new ArrayList<>();
    private Boolean accepted;
    private ReviewType reviewType;

    public static ReviewDto fromEntity(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setBaseResponse(review);
        dto.id = review.getId();
        dto.reviewer = UserResponseDto.fromEntity(review.getReviewer());
        dto.accepted = review.getAccepted();
        dto.reviewType = review.getReviewType();
        if (dto.reviewType == ReviewType.COMMENT) {
            List<CommentReviewDiff> entityList = review.getCommentDiffList();
            for (CommentReviewDiff item : entityList)
                dto.commentDiffList.add(CommentReviewDiffResponseDto.fromEntity(item));
        } else if (dto.reviewType == ReviewType.LIVE) {
            List<LiveReviewDiff> entityList = review.getLiveDiffList();
            for (LiveReviewDiff item : entityList)
                dto.liveDiffList.add(LiveReviewDiffResponseDto.fromEntity(item));
        }
        dto.threadList = review.getThreadList().stream().map(i -> ThreadResponseDto.fromEntity(i)).collect(Collectors.toList());
        return dto;
    }
}
