package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.ReviewReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewReservationRepository extends JpaRepository<ReviewReservation, Long>, ReviewReservationRepositoryCustom {

}
