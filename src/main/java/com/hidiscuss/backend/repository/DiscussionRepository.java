package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long>, DiscussionRepositoryCustom {
}
