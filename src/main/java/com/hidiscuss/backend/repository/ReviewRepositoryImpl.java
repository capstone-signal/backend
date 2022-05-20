package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.QReview;
import com.hidiscuss.backend.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QReview qReview = QReview.review;
//    private final QDiscussionCode qDiscussionCode = QDiscussionCode.discussionCode;

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Review findByIdFetchOrNull(Long id) {
        return queryFactory.selectFrom(qReview)
                .join(qReview.discussion).fetchJoin()
                .join(qReview.reviewer).fetchJoin()
                .where(qReview.id.eq(id))
                .fetchOne();
    }

    @Override
    public Page<Review> findAllByDiscussionIdFetch(Long discussionId, Pageable pageable) {
        List<Review> result = queryFactory.selectFrom(qReview)
                .where(qReview.discussion.id.eq(discussionId))
                .join(qReview.reviewer).fetchJoin()
//                .join(qReview.commentDiffList).fetchJoin()
//                .join(qReview.liveDiffList).fetchJoin()
                .join(qReview.discussion).fetchJoin()
//                .join(qDiscussionCode).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalSize = queryFactory.selectFrom(qReview)
                .where(qReview.discussion.id.eq(discussionId))
                .fetch().size();

        return new PageImpl<>(result, pageable, totalSize);
    }

    @Override
    public Optional<Review> findByReviewerId(Long userId) {
        return Optional.ofNullable(queryFactory.selectFrom(qReview)
                .where(qReview.reviewer.id.eq(userId))
                .fetchOne());
    }
}
