package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.QLiveReviewDiff;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LiveReviewDiffRepositoryImpl implements LiveReviewDiffRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QLiveReviewDiff qLiveReviewDiff = QLiveReviewDiff.liveReviewDiff;


    public LiveReviewDiffRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<LiveReviewDiff> findByReviewId(Long reviewId){
        return queryFactory.selectFrom(qLiveReviewDiff)
                .join(qLiveReviewDiff).fetchJoin()
                .where(qLiveReviewDiff.review.id.eq(reviewId))
                .fetch();
    }

}
