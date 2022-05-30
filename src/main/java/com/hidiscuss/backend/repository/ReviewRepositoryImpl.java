package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.QReview;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.utils.JPAUtil;
import com.querydsl.jpa.impl.JPAQuery;
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
        JPAQuery<Review> query = queryFactory.selectFrom(qReview)
                .where(qReview.discussion.id.eq(discussionId).and(qReview.isdone.eq(true)))
                .join(qReview.reviewer).fetchJoin()
                .join(qReview.discussion).fetchJoin();
        long totalSize = query.fetch().size();
        query = JPAUtil.paging(pageable, query, qReview);

        return new PageImpl<>(query.fetch(), pageable, totalSize);
    }

    @Override
    public Optional<Review> findByReviewerId(Long userId) {
        return Optional.ofNullable(queryFactory.selectFrom(qReview)
                .where(qReview.reviewer.id.eq(userId))
                .fetchOne());
    }
}
