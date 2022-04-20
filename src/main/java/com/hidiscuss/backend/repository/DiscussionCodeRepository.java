package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.DiscussionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscussionCodeRepository extends JpaRepository<DiscussionCode, Long>, DiscussionCodeRepositoryCustom {
    @Query("select d from DiscussionCode d left join fetch d.discussion where d.id = :id")
    Optional<DiscussionCode> findByIdFetchJoin(@Param("id") Long id);
}
