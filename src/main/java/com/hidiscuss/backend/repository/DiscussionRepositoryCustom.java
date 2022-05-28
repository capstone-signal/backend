package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.controller.dto.GetDiscussionsDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public interface DiscussionRepositoryCustom {
    Discussion findByIdFetchOrNull(Long id);

    Page<Discussion> findAllFilteredFetch(GetDiscussionsDto dto, Pageable pageable);

    Page<Discussion> findAllGroupByUser(User user, Pageable pageable);
}
