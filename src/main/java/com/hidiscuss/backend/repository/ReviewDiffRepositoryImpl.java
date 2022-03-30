package com.hidiscuss.backend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDiffRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public ReviewDiffRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
