package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.entity.DiscussionCode;

import java.util.List;

public interface DiscussionRepositoryCustom {
    List<DiscussionCode> findByIdListFetchJoin(List<Long> list);
}
