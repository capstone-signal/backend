package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.UserRankResponseDto;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No such user"));
    }

    public List<UserRankResponseDto> getUserRank() {
        List<UserRankResponseDto> rList = new ArrayList<>();
        List<User> userList = userRepository.findAllByOrderByPointDesc();
        int size = Math.min(userList.size(), 5);
        for (int i = 0; i < size; i++){
            UserRankResponseDto dto = UserRankResponseDto.builder()
                    .username(userList.get(i).getName())
                    .point(userList.get(i).getPoint())
                    .build();
            rList.add(dto);
        }
        return rList;
    }
}
