package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.QDiscussionCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiscussionCodeRepositoryImpl implements DiscussionRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QDiscussionCode qDiscussionCode = QDiscussionCode.discussionCode;

    public DiscussionCodeRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DiscussionCode> findByIdListFetchJoin(List<Long> list) {
        List<DiscussionCode> result = queryFactory.selectFrom(qDiscussionCode)
                .where(qDiscussionCode.id.in(list))
                .fetch();
        return result;
    }

}
