package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.ReviewThread;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class CreateThreadRequestDto {

    @NotNull
    public String content;

    public static ReviewThread toEntity(CreateThreadRequestDto dto) {
        return ReviewThread.builder()
                .content(dto.content)
                .build();
    }
}
