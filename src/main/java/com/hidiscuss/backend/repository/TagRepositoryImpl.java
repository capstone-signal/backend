package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.controller.dto.DiscussionTagDto;
import com.hidiscuss.backend.controller.dto.TagResponseDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionTag;
import com.hidiscuss.backend.entity.QDiscussion;
import com.hidiscuss.backend.entity.QTag;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QTag qTag = QTag.tag;

    public TagRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DiscussionTagDto> findAllByName(List<String> tags) {
        return queryFactory.select(Projections.constructor(DiscussionTagDto.class, qTag.id, qTag.name))
                .from(qTag)
                .where(qTag.name.in(tags))
                .fetch();
    }
}
