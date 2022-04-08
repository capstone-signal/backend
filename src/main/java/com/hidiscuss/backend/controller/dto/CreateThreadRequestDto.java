package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.ReviewThread;

import javax.validation.constraints.NotNull;

public class CreateThreadRequestDto {

    @NotNull
    public Long reviewId;

    @NotNull
    public String content;

    public static ReviewThread toEntity(CreateThreadRequestDto dto) {
        return ReviewThread.builder()
                //.review(dto.reviewId)
                .content(dto.content)
                .build();
    }
}
