package com.hidiscuss.backend.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = false)
    private Timestamp lastModifiedAt;

    @Column(name = "created_user_id")
    private Long createdUserId;

    @Column(name = "modified_user_id")
    private Long modifiedUserId;


}
