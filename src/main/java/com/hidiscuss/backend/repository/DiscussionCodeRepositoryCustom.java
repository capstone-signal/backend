package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.DiscussionCode;

import java.util.List;

public interface DiscussionCodeRepositoryCustom {
    List<DiscussionCode> findByIdList(List<Long> list);
}

