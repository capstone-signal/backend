package com.hidiscuss.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateCommentReviewRequestDto {

    @NotNull
    public Long discussionId;

    @NotNull
    public List<CommentReviewDiffDto> diffList;

}
