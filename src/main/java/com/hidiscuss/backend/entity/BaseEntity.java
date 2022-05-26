package com.hidiscuss.backend.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {

    @Column(name = "created_at", updatable = false, nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "last_modified_at", nullable = false)
    private ZonedDateTime lastModifiedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.lastModifiedAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.lastModifiedAt = ZonedDateTime.now();
    }
}
