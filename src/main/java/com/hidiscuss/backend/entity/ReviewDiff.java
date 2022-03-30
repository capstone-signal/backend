package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ReviewDiff")
public class ReviewDiff extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "discussion_code_id", nullable = false)
    private DiscussionCode discussionCode;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_code", nullable = false)
    private String updatedCode;

    @Builder
    public ReviewDiff(Long id, Review review, DiscussionCode discussionCode, String comment, String updatedCode) {
        this.id = id;
        this.review = review;
        this.discussionCode = discussionCode;
        this.comment = comment;
        this.updatedCode = updatedCode;
    }
}
