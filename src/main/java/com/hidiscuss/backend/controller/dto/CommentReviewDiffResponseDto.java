package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class CommentReviewDiffResponseDto extends ReviewDiffResponseDto {
    @NotNull
    public String codeLocate;

    @Nullable
    public String comment;

    public static CommentReviewDiffResponseDto fromEntity(CommentReviewDiff entity) {
        CommentReviewDiffResponseDto dto = new CommentReviewDiffResponseDto();
        dto.setBaseResponse(entity);
        dto.id = entity.getId();
        dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
        dto.codeAfter = entity.getCodeAfter();
        dto.codeLocate = entity.getCodeLocate();
        dto.comment = entity.getComment();
        return dto;
    }
}
