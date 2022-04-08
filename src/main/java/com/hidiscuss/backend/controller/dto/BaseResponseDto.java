package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.BaseEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public void setBaseResponse(BaseEntity entity) {
        this.createdAt = entity.getCreatedAt();
        this.lastModifiedAt = entity.getLastModifiedAt();
    }
}
