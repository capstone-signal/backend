package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findById(Long userId);

    Optional<User> findByName(String autobotName);

    List<User> findTop5ByOrderByPointDesc();
}