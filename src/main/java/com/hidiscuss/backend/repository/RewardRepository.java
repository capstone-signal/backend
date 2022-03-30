package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long>, RewardRepositoryCustom {
}
