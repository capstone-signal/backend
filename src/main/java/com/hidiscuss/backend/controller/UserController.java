package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value="테스트 사용자 호출", notes="이 api는 테스트 api입니다. 여기에 api 동작 설명을 작성해주세요.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/userlist")
    public ResponseEntity<List<User>> getUserList() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatus.OK);
    }
}
