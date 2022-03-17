package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.Entity.QUser;
import com.hidiscuss.backend.Entity.User;
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