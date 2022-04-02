package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.ReviewThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewThreadRepository extends JpaRepository<ReviewThread, Long>, ReviewThreadRepositoryCustom {
}
