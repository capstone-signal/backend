package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.QReview;
import com.hidiscuss.backend.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QReview qReview = QReview.review;

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Review findByIdFetchOrNull(Long id) {
        return queryFactory.selectFrom(qReview)
                .where(qReview.id.eq(id))
                .fetchOne();
    }
}
