package com.hidiscuss.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class DiscussionDetailResponseDto {
    private DiscussionResponseDto discussionResponseDto;
    private Page<ReviewDto> reviewResponseDtoPage;
    private Long userAuthority;
}
