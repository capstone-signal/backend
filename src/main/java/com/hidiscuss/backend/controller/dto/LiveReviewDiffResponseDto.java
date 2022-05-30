package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.LiveReviewDiff;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LiveReviewDiffResponseDto extends ReviewDiffResponseDto {
    public static LiveReviewDiffResponseDto fromEntity(LiveReviewDiff entity) {
        LiveReviewDiffResponseDto dto = new LiveReviewDiffResponseDto();
        dto.id = entity.getId();
        dto.discussionCode = entity.getDiscussionCode().getId();
        dto.codeAfter = entity.getCodeAfter();
        return dto;
    }
}
