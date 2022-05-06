package com.hidiscuss.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateLiveReviewRequestDto {
    @NotNull
    public Long discussionId;

    @NotNull
    public List<CreateLiveReviewDiffDto> diffList;

}
