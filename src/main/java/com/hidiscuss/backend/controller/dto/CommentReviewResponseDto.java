package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentReviewResponseDto extends BaseResponseDto {

    private Long id;

    private UserResponseDto reviewer;

    private DiscussionResponseDto discussion;

    private List<CommentReviewDiffDto> diffList;

    private Boolean accepted;

    private ReviewType reviewType;

    public static CommentReviewResponseDto fromEntity(Review entity) {
        CommentReviewResponseDto dto = new CommentReviewResponseDto();
        dto.id = entity.getId();
        dto.reviewer = UserResponseDto.fromEntity(entity.getReviewer());
        dto.discussion = DiscussionResponseDto.fromEntity(entity.getDiscussion());
        for(CommentReviewDiff item: entity.getDiffList()) {
            dto.diffList.add(CommentReviewDiffDto.fromEntity(item));
        }
        dto.accepted = entity.getAccepted();
        dto.reviewType = entity.getReviewType();
        return dto;
    }
}
