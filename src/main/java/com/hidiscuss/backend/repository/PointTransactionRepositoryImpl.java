package com.hidiscuss.backend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PointTransactionRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public PointTransactionRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
