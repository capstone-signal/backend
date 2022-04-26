package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.Tag;
import lombok.Getter;

@Getter
public class TagResponseDto {
    private Long id;
    private String name;

    private TagResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public static TagResponseDto fromEntity(Tag tag) {
        TagResponseDto dto = new TagResponseDto(tag.getId(), tag.getName());
        return dto;
    }
}
