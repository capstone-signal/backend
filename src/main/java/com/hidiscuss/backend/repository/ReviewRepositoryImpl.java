package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.QDiscussion;
import com.hidiscuss.backend.entity.QReview;
import com.hidiscuss.backend.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QReview qReview = QReview.review;
//    private final QDiscussion qDiscussion = QDiscussion.discussion;

    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Review findByIdFetchOrNull(Long id) {
        return queryFactory.selectFrom(qReview)
                .join(qReview.discussion).fetchJoin()
                .join(qReview.reviewer).fetchJoin()
                .where(qReview.id.eq(id))
                .fetchOne();
    }

    @Override
    public Page<Review> findAllByDiscussionIdFetch(Long discussionId, PageRequest pageRequest) {
        List<Review> result = queryFactory.selectFrom(qReview)
                .where(qReview.discussion.id.eq(discussionId))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();
        return new PageImpl<>(result, pageRequest, result.size());
    }
}
