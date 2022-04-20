package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LiveReviewResponseDto {

    private Long id;

    private UserResponseDto reviewer;

    private DiscussionResponseDto discussion;

    private List<LiveReviewDiffDto> diffList = new ArrayList<>();

    private Boolean accepted;

    private ReviewType reviewType;

    public static LiveReviewResponseDto fromEntity(Review entity) {
        LiveReviewResponseDto dto = new LiveReviewResponseDto();
        dto.id = entity.getId();
        dto.reviewer = UserResponseDto.fromEntity(entity.getReviewer());
        dto.discussion = DiscussionResponseDto.fromEntity(entity.getDiscussion());

        dto.accepted = entity.getAccepted();
        dto.reviewType = entity.getReviewType();
        return dto;
    }
}
