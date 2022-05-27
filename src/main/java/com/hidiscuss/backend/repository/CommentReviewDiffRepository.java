package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReviewDiffRepository extends JpaRepository<CommentReviewDiff, Long>, CommentReviewDiffRepositoryCustom{
}
