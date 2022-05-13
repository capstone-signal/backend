package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentReviewDiffDto {

    @NotNull
    public DiscussionCodeDto discussionCode;

    @NotNull
    public String codeAfter;

    @NotNull
    public String codeLocate;

    @Nullable
    public String comment;

    public static CommentReviewDiff toEntity(CreateCommentReviewDiffDto dto, Review review, DiscussionCode code) {
        return CommentReviewDiff.builder()
                .review(review)
                .discussionCode(code)
                .codeAfter(dto.getCodeAfter())
                .codeLocate(dto.getCodeLocate())
                .comment(dto.getComment())
                .build();
    }

    public static CreateCommentReviewDiffDto fromEntity(CommentReviewDiff entity) {
        CreateCommentReviewDiffDto dto = new CreateCommentReviewDiffDto();
        dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
        dto.codeAfter = entity.getCodeAfter();
        dto.codeLocate = entity.getCodeLocate();
        dto.comment = entity.getComment();

        return dto;
    }
}
