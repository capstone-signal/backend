package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "LiveReviewDiff")
public class LiveReviewDiff extends ReviewDiff {

    @Builder
    public LiveReviewDiff(Long id, Review review, DiscussionCode discussionCode, String codeAfter) {
        super(id, review, discussionCode, codeAfter);
    }

    public LiveReviewDiff() {
        super();
    }

    public void setReview(Review review) {
        super.review = review;
        review.getLiveDiffList().add(this);
    }

    public void setCodeAfter(String codeAfter){
        super.setCodeAfter(codeAfter);
    }

    @Override
    public String toString() {
        return "LiveReviewDiff{" +
                '}';
    }

}
