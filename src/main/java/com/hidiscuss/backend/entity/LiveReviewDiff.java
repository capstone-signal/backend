package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "LiveReviewDiff")
public class LiveReviewDiff extends ReviewDiff {

    @Column(name = "code_locate", nullable = false)
    private String codeLocate;

    @Builder
    public LiveReviewDiff(Long id, Review review, DiscussionCode discussionCode, String codeAfter, String codeLocate, String comment) {
        super(id, review, discussionCode, codeAfter);
        this.codeLocate = codeLocate;
    }

    public LiveReviewDiff() {
        super();
    }
}
