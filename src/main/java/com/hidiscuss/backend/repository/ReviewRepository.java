package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    @Query("select r from Review r left join fetch r.reviewer left join fetch r.discussion")
    Optional<Review> findByIdFetchJoin(Long reviewId);
}
