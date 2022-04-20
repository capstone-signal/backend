package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User getByName(String name);
}