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


    public List<UserRankResponseDto> getTopFiveUSer() {
        List<UserRankResponseDto> rList = new ArrayList<>();
        List<User> userList = userRepository.findTop5ByOrderByPointDesc();
        int size = Math.min(userList.size(), 5);
        for (int i = 0; i < size; i++) {
            UserRankResponseDto dto = UserRankResponseDto.toEntity(userList.get(i).getName(), userList.get(i).getPoint());
            rList.add(dto);
        }
        return rList;
    }

    public User getAutobot() {
        return userRepository.findByName(AUTOBOT_NAME).orElse(null);
    }
}
