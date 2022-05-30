package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.UserRepository;
import com.hidiscuss.backend.utils.DbInitilization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public User getAutobot() {
        return userRepository.findByName(AUTOBOT_NAME).orElse(null);
    }
}
