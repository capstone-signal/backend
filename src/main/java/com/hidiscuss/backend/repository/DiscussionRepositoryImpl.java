package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.QDiscussion;
import com.hidiscuss.backend.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiscussionRepositoryImpl implements DiscussionRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QDiscussion qDiscussion = QDiscussion.discussion;
    private final QUser qUser = QUser.user;

    public DiscussionRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Discussion findByIdFetchOrNull(Long id) {
        return queryFactory.selectFrom(qDiscussion)
                .join(qDiscussion.user, qUser).fetchJoin()
                .where(qDiscussion.id.eq(id))
                .fetchOne();
    }
}
