package com.hidiscuss.backend.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ReviewDiffResponseDto extends BaseResponseDto {

    @NotNull
    public Long id;

    @NotNull
    public Long discussionCode;

    @NotNull
    public String codeAfter;


}
