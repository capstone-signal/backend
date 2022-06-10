package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.QUser;
import com.hidiscuss.backend.entity.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QUser user = QUser.user;

    public List<User> getUserList() {
        return queryFactory
                .selectFrom(user)
                .fetch();
    }
}