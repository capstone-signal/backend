package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentReviewDiffDto extends ReviewDiffDto {

    @NotNull
    public String codeLocate;

    @Nullable
    public String comment;

    public static CommentReviewDiff toEntity(CommentReviewDiffDto dto, Review review, DiscussionCode code) {
        return CommentReviewDiff.builder()
                .review(review)
                .discussionCode(code)
                .codeAfter(dto.getCodeAfter())
                .codeLocate(dto.getCodeLocate())
                .comment(dto.getComment())
                .build();
    }

    public static CommentReviewDiffDto fromEntity(CommentReviewDiff entity) {
        CommentReviewDiffDto dto = new CommentReviewDiffDto();
        dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
        dto.codeAfter = entity.getCodeAfter();
        dto.codeLocate = entity.getCodeLocate();
        dto.comment = entity.getComment();

        return dto;
    }
}
