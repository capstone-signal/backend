package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class LiveReviewResponseDto {

    private Long id;

    private UserResponseDto reviewer;

    private DiscussionResponseDto discussion;

    private List<CreateLiveReviewDiffDto> diffList = new ArrayList<>();

    private Boolean accepted;

    private ReviewType reviewType;

    public static LiveReviewResponseDto fromEntity(Review entity) {
        LiveReviewResponseDto dto = new LiveReviewResponseDto();
        dto.id = entity.getId();
        dto.reviewer = UserResponseDto.fromEntity(entity.getReviewer());
        dto.discussion = DiscussionResponseDto.fromEntity(entity.getDiscussion());
        Optional<List<LiveReviewDiff>> list = Optional.ofNullable(entity.getLiveDiffList());
        if (list.isPresent()) {
            for(LiveReviewDiff item: entity.getLiveDiffList()) {
                dto.diffList.add(CreateLiveReviewDiffDto.fromEntity(item));
            }
        }
        dto.accepted = entity.getAccepted();
        dto.reviewType = entity.getReviewType();
        return dto;
    }
}
