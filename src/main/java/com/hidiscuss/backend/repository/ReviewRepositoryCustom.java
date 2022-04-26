package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Review findByIdFetchOrNull(Long id);

    Page<Review> findAllByDiscussionIdFetch(Long id, Pageable pageable);
}
