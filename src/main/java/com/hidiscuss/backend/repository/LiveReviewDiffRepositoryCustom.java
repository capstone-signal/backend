package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.LiveReviewDiff;

import java.util.List;

public interface LiveReviewDiffRepositoryCustom {
    List<LiveReviewDiff> findByReviewId(Long reviewId);
}
