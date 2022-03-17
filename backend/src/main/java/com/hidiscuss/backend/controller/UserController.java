package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userlist")
    public ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }
}
