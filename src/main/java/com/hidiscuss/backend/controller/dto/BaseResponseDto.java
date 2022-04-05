package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.BaseEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseResponseDto {
    private LocalDateTime created_at;
    private LocalDateTime last_modified_at;

    public void setBaseResponse(BaseEntity entity) {
        this.created_at = entity.getCreatedAt();
        this.last_modified_at = entity.getLastModifiedAt();
    }
}
