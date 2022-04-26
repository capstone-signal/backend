package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.LiveReviewDiff;
import lombok.Getter;

@Getter
public class LiveReviewDiffDto extends ReviewDiffDto {
    public static LiveReviewDiffDto fromEntity(LiveReviewDiff entity) {
        LiveReviewDiffDto dto = new LiveReviewDiffDto();
        dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
        dto.codeAfter = entity.getCodeAfter();
        return dto;
    }
}
