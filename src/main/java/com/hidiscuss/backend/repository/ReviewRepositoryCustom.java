package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {
    Review findByIdFetchOrNull(Long id);

    Page<Review> findAllByDiscussionIdFetch(Long id, Pageable pageable);

    Optional<Review> findByReviewerId(Long userId);

    List<Review> findByIds(List<Long> ids);
}
