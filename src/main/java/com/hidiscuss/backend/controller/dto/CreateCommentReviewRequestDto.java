package com.hidiscuss.backend.controller.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateCommentReviewRequestDto {

    @NotNull
    public Long discussionId;

    @NotNull
    public List<CommentReviewDiffDto> diffList;

}
