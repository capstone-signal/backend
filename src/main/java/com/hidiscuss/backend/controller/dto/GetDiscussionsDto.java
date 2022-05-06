package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@ApiModel
public class GetDiscussionsDto implements Serializable {
    private DiscussionState state;

    private String keyword;

    private List<String> tags;

    private boolean onlyMine;

    public GetDiscussionsDto(DiscussionState state, String keyword, List<String> tags, boolean onlyMine) {
        this.state = state;
        this.keyword = keyword;
        this.tags = tags;
        this.onlyMine = onlyMine;
    }

    @Nullable
    private Long userId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
