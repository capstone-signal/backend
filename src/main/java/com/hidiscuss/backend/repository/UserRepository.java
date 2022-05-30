package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findById(Long userId);
    Optional<User> findByName(String autobotName);
}