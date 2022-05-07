package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class DiscussionCodeDto {
    public Long id;
    public String filename;
    public String content;
    public String language;

    public static DiscussionCodeDto fromEntity(DiscussionCode entity) {
        DiscussionCodeDto dto = new DiscussionCodeDto(entity.getId(), entity.getFilename(), entity.getContent(), entity.getLanguage());
        return dto;
    }

    public static List<DiscussionCodeDto> fromEntityList(List<DiscussionCode> entityList) {
        return entityList.stream()
                .map(DiscussionCodeDto::fromEntity)
                .collect(Collectors.toList());
    }
}
