package com.hidiscuss.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class DiscussionDetailResponseDto {
    private DiscussionResponseDto discussionResponseDto;
    private List<DiscussionCodeDto> discussionCodeDtoList;
    private Long userAuthority;
}
