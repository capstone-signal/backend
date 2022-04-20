package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.LiveReviewDiff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveReviewDiffRepository extends JpaRepository<LiveReviewDiff, Long>, LiveReviewDiffRepositoryCustom {
    LiveReviewDiff findByReviewId(Long id);
}
