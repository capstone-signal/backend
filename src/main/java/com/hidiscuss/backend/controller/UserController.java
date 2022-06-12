package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.UserRankResponseDto;
import com.hidiscuss.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value="유저 랭크 반환", notes="이 api는 포인트순으로 정렬된 유저 5명을 반환합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/ranking")
    public List<UserRankResponseDto> getTopFiveUSer() {
        return userService.getTopFiveUSer();
    }
}
