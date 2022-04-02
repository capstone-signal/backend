package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long>, PointTransactionRepositoryCustom {
}
