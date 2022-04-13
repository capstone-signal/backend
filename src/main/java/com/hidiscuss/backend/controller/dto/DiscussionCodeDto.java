package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class DiscussionCodeDto {
    public Long id;
    public String filename;
    public String content;

    public static DiscussionCodeDto fromEntity(DiscussionCode entity) {
        DiscussionCodeDto dto = new DiscussionCodeDto(entity.getId(), entity.getFilename(), entity.getContent());
        return dto;
    }
}
