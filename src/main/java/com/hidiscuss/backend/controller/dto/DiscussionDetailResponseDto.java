package com.hidiscuss.backend.controller.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class DiscussionDetailResponseDto {
    private DiscussionResponseDto discussionResponseDto;
    private Page<ReviewDto> reviewResponseDtoPage;

    @Builder
    public DiscussionDetailResponseDto(DiscussionResponseDto discussionResponseDto, Page<ReviewDto> reviewResponseDtoPage) {
        this.discussionResponseDto = discussionResponseDto;
        this.reviewResponseDtoPage = reviewResponseDtoPage;
    }
}
