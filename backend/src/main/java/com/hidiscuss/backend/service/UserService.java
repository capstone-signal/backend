package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUserList() {
        return userRepository.getUserList();
    }
}
