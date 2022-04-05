package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CommentReviewDiff")
public class CommentReviewDiff extends ReviewDiff {

    @Column(name = "code_locate", nullable = false)
    private String code_locate;

    @Column(name = "comment", nullable = false)
    private String comment;

}
