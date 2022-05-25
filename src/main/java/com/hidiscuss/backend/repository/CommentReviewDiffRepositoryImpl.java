package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.QCommentReviewDiff;
import com.hidiscuss.backend.entity.ReviewDiff;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentReviewDiffRepositoryImpl implements CommentReviewDiffRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QCommentReviewDiff qCommentReviewDiff = QCommentReviewDiff.commentReviewDiff;

    public CommentReviewDiffRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<CommentReviewDiff> findByReviewId(Long reviewId){
        return queryFactory.selectFrom(qCommentReviewDiff)
                .join(qCommentReviewDiff).fetchJoin()
                .where(qCommentReviewDiff.review.id.eq(reviewId))
                .fetch();
    }
}
