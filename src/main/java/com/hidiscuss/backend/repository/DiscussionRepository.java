package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiscussionRepository extends JpaRepository<Discussion, Long>, DiscussionRepositoryCustom {
    @Query("select d from Discussion d left join fetch d.user")
    Optional<Discussion> findByIdFetchJoin(Long id);
}
