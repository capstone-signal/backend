package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.QDiscussionCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiscussionCodeRepositoryImpl implements DiscussionCodeRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QDiscussionCode qDiscussionCode = QDiscussionCode.discussionCode;

    public DiscussionCodeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DiscussionCode> findByIdList(List<Long> list) {
        return queryFactory.selectFrom(qDiscussionCode)
                .where(qDiscussionCode.id.in(list))
                .fetch();
    }
}
