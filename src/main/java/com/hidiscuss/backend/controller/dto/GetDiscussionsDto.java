package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionState;
import lombok.Getter;

import java.util.List;

@Getter
public class GetDiscussionsDto {
    private DiscussionState state;
    private String keyword;
    private List<DiscussionTagDto> tags;
    private Long userId;

    public GetDiscussionsDto(DiscussionState state, String keyword, List<DiscussionTagDto> tags) {
        this.state = state;
        this.keyword = keyword;
        this.tags = tags;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
