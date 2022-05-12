package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.QReviewReservation;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public List<ReviewReservation> findByDiscussionIdAndUserId(Long discussionId, Long userId){
        return queryFactory.selectFrom(qReviewReservation)
                .where(qReviewReservation.discussion.id.eq(discussionId)
                        .and(qReviewReservation.reviewer.id.eq(userId).or(qReviewReservation.discussion.user.id.eq(userId)))
                        .and(qReviewReservation.reviewStartDateTime.before(LocalDateTime.now()))
                        .and(qReviewReservation.reviewStartDateTime.after(LocalDateTime.now().minusHours(1L))))
                .fetch();
    }

}
