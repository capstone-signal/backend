package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionTag;
import com.hidiscuss.backend.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiscussionTagDto {

    private Long id;

    private String name;

    public DiscussionTagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DiscussionTagDto fromEntity(DiscussionTag discussionTag) {
        DiscussionTagDto dto = new DiscussionTagDto();
        dto.id = discussionTag.getTag().getId();
        dto.name = discussionTag.getTag().getName();
        return dto;
    }

    public static DiscussionTag toEntity(DiscussionTagDto dto) {
        return DiscussionTag.builder().tag(new Tag(dto.getId(), dto.getName())).build();
    }
}
