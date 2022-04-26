package com.hidiscuss.backend.controller.dto;

import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
public class ReviewDiffDto extends BaseResponseDto {

    @NotNull
    public DiscussionCodeDto discussionCode;

    @NotNull
    public String codeAfter;


}
