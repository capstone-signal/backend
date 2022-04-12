package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Review;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentReviewDiffDto {

    @NotNull
    public Long discussionCodeId;

    @NotNull
    public String codeAfter;

    @NotNull
    public String codeLocate;

    @NotNull
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

}
