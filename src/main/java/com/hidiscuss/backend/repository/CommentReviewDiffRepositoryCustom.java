package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.ReviewDiff;

import java.util.List;

public interface CommentReviewDiffRepositoryCustom {

    List<CommentReviewDiff> findByReviewId(Long reviewId);
}
