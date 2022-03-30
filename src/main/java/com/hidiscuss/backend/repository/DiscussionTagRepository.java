package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.DiscussionTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionTagRepository extends JpaRepository<DiscussionTag, Long>, DiscussionTagRepositoryCustom {
}
