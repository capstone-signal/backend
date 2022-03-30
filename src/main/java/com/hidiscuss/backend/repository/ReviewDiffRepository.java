package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.ReviewDiff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDiffRepository extends JpaRepository<ReviewDiff, Long>, ReviewDiffRepositoryCustom {
}
