package com.hidiscuss.backend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LiveReviewDiffRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public LiveReviewDiffRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
