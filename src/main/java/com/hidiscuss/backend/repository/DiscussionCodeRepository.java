package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.DiscussionCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionCodeRepository extends JpaRepository<DiscussionCode, Long>, DiscussionCodeRepositoryCustom {
}
