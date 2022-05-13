package com.hidiscuss.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "CommentReviewDiff")
public class CommentReviewDiff extends ReviewDiff {

    @Column(/*columnDefinition = "json", */name = "code_locate", nullable = false)
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
        review.getCommentDiffList().add(this);
    }

    @Override
    public String toString() {
        return "CommentReviewDiff{" +
                "codeLocate='" + codeLocate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
