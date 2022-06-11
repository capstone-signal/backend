package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.UserRankResponseDto;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.UserRepository;
import com.hidiscuss.backend.utils.DbInitilization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public static final String AUTOBOT_NAME = "AutoBot";

    @PostConstruct
    private void initAutobotService() {
        User autobot = this.getAutobot();
        if (autobot != null) {
            return;
        }
        log.info("Initializing Autobot service");
        autobot = DbInitilization.getInitialAutobot();
        userRepository.save(autobot);
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

    public User getAutobot() {
        return userRepository.findByName(AUTOBOT_NAME).orElse(null);

    }
}
