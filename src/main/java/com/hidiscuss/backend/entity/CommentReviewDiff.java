package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "CommentReviewDiff")
public class CommentReviewDiff extends ReviewDiff {

    @Column(name = "code_locate", nullable = false)
    private String codeLocate;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Builder
    public CommentReviewDiff(Long id, Review review, DiscussionCode discussionCode, String codeAfter, String codeLocate, String comment) {
        super(id, review, discussionCode, codeAfter);
        this.codeLocate = codeLocate;
        this.comment = comment;
    }

    public CommentReviewDiff() {
        super();
    }

    public void setReview(Review review) {
        super.review = review;
        review.getDiffList().add(this);
    }

    @Override
    public String toString() {
        return "CommentReviewDiff{" +
                "codeLocate='" + codeLocate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
