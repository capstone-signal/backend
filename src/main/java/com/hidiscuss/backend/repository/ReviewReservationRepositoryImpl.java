package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.QReviewReservation;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

    @Override
    public List<ReviewReservation> findByUserId(Long userId){
        ZonedDateTime now = ZonedDateTime.now();
        return queryFactory.selectFrom(qReviewReservation)
                .join(qReviewReservation.discussion).fetchJoin()
                .join(qReviewReservation.reviewer).fetchJoin()
                .where(qReviewReservation.reviewer.id.eq(userId).or(qReviewReservation.discussion.user.id.eq(userId))
                        .and(qReviewReservation.reviewStartDateTime.before(now)
                        .and(qReviewReservation.reviewStartDateTime.after(now.minusHours(1L)))))
                .fetch();
    }

}
