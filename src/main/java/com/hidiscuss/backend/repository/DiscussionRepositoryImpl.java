package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.controller.dto.GetDiscussionsDto;
import com.hidiscuss.backend.entity.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.QDiscussion;
import com.hidiscuss.backend.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DiscussionRepositoryImpl implements DiscussionRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QDiscussion qDiscussion = QDiscussion.discussion;
    private final QDiscussionTag qDiscussionTag = QDiscussionTag.discussionTag;
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

    @Override
    public Page<Discussion> findAllFilteredFetch(GetDiscussionsDto dto, Pageable pageable) {
        JPAQuery<Discussion> query = queryFactory.selectFrom(qDiscussion)
                .join(qDiscussionTag)
                .on(qDiscussion.eq(qDiscussionTag.discussion))
                .groupBy(qDiscussion);

        if (dto.getTags().size() != 0)
            query.where(qDiscussionTag.tag.id.in(dto.getTags().stream().map(i -> Long.parseLong(i)).collect(Collectors.toList())));

        Optional<String> discussionKeyword = Optional.ofNullable(dto.getKeyword());
        if (discussionKeyword.isPresent())
            query.where(qDiscussion.title.contains(dto.getKeyword()));

        Optional<DiscussionState> discussionState = Optional.ofNullable(dto.getState());
        if (discussionState.isPresent())
            query.where(qDiscussion.state.eq(dto.getState()));

        Optional<Long> userId = Optional.ofNullable(dto.getUserId());
        if (userId.isPresent())
            query.where(qDiscussion.user.id.eq(userId.get()));

        long totalSize = query.fetch().size();

        query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(qDiscussion.getType(), qDiscussion.getMetadata());
            OrderSpecifier orderSpecifier = new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty()));
            query.orderBy(orderSpecifier);
        }
        return new PageImpl<>(query.fetch(), pageable, totalSize);
    }
}
