package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.QReviewReservation;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewReservationRepositoryImpl implements ReviewReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QReviewReservation qReviewReservation = QReviewReservation.reviewReservation;

    public ReviewReservationRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ReviewReservation> findByDiscussionId(Long discussionId) {
        return queryFactory.selectFrom(qReviewReservation)
                .where(qReviewReservation.discussion.id.eq(discussionId))
                .fetch();
    }
}
