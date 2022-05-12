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
    @Nullable private DiscussionState state;
    @Nullable private String keyword;
    @Nullable private List<String> tags;
    @Nullable private Boolean onlyMine;

    public GetDiscussionsDto(DiscussionState state, String keyword, List<String> tags, Boolean onlyMine) {
        this.state = state;
        this.keyword = keyword;
        this.tags = new ArrayList<>();
        if (tags != null) this.tags = tags;
        if (onlyMine == null){
            this.onlyMine = false;
        } else {
            this.onlyMine = onlyMine;
        }
    }

    @Nullable private Long userId;

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
