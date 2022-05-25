package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.LiveReviewDiff;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LiveReviewDiffResponseDto extends ReviewDiffResponseDto {
    public static LiveReviewDiffResponseDto fromEntity(LiveReviewDiff entity) {
        LiveReviewDiffResponseDto dto = new LiveReviewDiffResponseDto();
        dto.id = entity.getId();
        dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
        dto.codeAfter = entity.getCodeAfter();
        return dto;
    }

    public static List<LiveReviewDiffResponseDto> fromEntityList(List<LiveReviewDiff> entityList) {
        List<LiveReviewDiffResponseDto> dtos = new ArrayList<>();

        for (LiveReviewDiff entity : entityList){
            LiveReviewDiffResponseDto dto = new LiveReviewDiffResponseDto();
            dto.id = entity.getId();
            dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
            dto.codeAfter = entity.getCodeAfter();
            dtos.add(dto);
        }
        return dtos;
    }
}
