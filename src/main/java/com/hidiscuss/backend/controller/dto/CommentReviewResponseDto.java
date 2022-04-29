package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class CommentReviewResponseDto {

    private Long id;

    private UserResponseDto reviewer;

    private DiscussionResponseDto discussion;

    private List<CreateCommentReviewDiffDto> diffList = new ArrayList<>();

    private Boolean accepted;

    private ReviewType reviewType;

    public static CommentReviewResponseDto fromEntity(Review entity) {
        CommentReviewResponseDto dto = new CommentReviewResponseDto();
        dto.id = entity.getId();
        dto.reviewer = UserResponseDto.fromEntity(entity.getReviewer());
        dto.discussion = DiscussionResponseDto.fromEntity(entity.getDiscussion());
        Optional<List<CommentReviewDiff>> list = Optional.ofNullable(entity.getCommentDiffList());
        if (list.isPresent()) {
            for(CommentReviewDiff item: entity.getCommentDiffList()) {
                dto.diffList.add(CreateCommentReviewDiffDto.fromEntity(item));
            }
        }
        dto.accepted = entity.getAccepted();
        dto.reviewType = entity.getReviewType();
        return dto;
    }
}
