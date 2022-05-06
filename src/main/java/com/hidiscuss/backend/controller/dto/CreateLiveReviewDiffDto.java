package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLiveReviewDiffDto {

    @NotNull
    public DiscussionCodeDto discussionCode;

    @NotNull
    public String codeAfter;

    public static LiveReviewDiff toEntity(CreateLiveReviewDiffDto dto, Review review, DiscussionCode code) {
        return LiveReviewDiff.builder()
                .review(review)
                .discussionCode(code)
                .codeAfter(dto.getCodeAfter())
                .build();
    }

    public static CreateLiveReviewDiffDto fromEntity(LiveReviewDiff entity) {
        CreateLiveReviewDiffDto dto = new CreateLiveReviewDiffDto();
        dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
        dto.codeAfter = entity.getCodeAfter();
        return dto;
    }
}
