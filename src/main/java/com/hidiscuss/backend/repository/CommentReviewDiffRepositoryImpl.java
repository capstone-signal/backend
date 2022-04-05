package com.hidiscuss.backend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CommentReviewDiffRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public CommentReviewDiffRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
