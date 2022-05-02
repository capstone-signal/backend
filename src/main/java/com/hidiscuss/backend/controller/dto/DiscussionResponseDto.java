package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.LiveReviewAvailableTimes;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DiscussionResponseDto extends BaseResponseDto {
    private Long id;
    private String title;
    private String question;
    private Boolean liveReviewRequired;
    private LiveReviewAvailableTimes liveReviewAvailableTimes;
    private Long priority;
    private int state;
    private UserResponseDto user;
    private List<TagResponseDto> tags;
    public static DiscussionResponseDto fromEntity(Discussion entity) {
        DiscussionResponseDto dto = new DiscussionResponseDto();
        dto.setBaseResponse(entity);
        dto.id = entity.getId();
        dto.title = entity.getTitle();
        dto.question = entity.getQuestion();
        dto.liveReviewRequired = entity.getLiveReviewRequired();
        dto.liveReviewAvailableTimes = entity.getLiveReviewAvailableTimes();
        dto.priority = entity.getPriority();
        dto.state = entity.getState().getId();
        dto.tags = entity.getTags().stream().map(TagResponseDto::fromEntity).collect(Collectors.toList());
        dto.user = UserResponseDto.fromEntity(entity.getUser());
        return dto;
    }
}
