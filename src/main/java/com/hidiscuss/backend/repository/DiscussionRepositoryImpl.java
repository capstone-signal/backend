package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.controller.dto.GetDiscussionsDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.utils.JPAUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.QDiscussion;
import com.hidiscuss.backend.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DiscussionRepositoryImpl implements DiscussionRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QDiscussion qDiscussion = QDiscussion.discussion;
    private final QDiscussionTag qDiscussionTag = QDiscussionTag.discussionTag;
    private final QTag qTag = QTag.tag;
    private final QUser qUser = QUser.user;
    private final QReview qReview = QReview.review;

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
        query = JPAUtil.paging(pageable, query, qDiscussion);

        return new PageImpl<>(query.fetch(), pageable, totalSize);
    }

    @Override
    public Page<Discussion> findByReviewedUserDistinct(User user, Pageable pageable) {
        JPAQuery<Discussion> query = queryFactory
                .selectFrom(qDiscussion)
                .distinct()
                .innerJoin(qReview).on(qDiscussion.id.eq(qReview.discussion.id))
                .join(qDiscussionTag).on(qDiscussion.eq(qDiscussionTag.discussion)).fetchJoin()
                .join(qTag).on(qDiscussionTag.tag.eq(qTag))
                .where(qReview.reviewer.id.eq(user.getId()));
        long totalSize = query.fetch().size();
        query = JPAUtil.paging(pageable, query, qDiscussion);
        // TODO: tag 쿼리 최적화
        return new PageImpl<>(query.fetch(), pageable, totalSize);
    }


}
