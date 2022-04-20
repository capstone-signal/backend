package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.controller.dto.ThreadResponseDto;
import com.hidiscuss.backend.entity.QLiveReviewDiff;
import com.hidiscuss.backend.entity.ReviewThread;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.service.LiveReviewService;
import com.hidiscuss.backend.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/liveReview")
public class LivReviewController {

    private final LiveReviewService liveReviewService;

    public LivReviewController(LiveReviewService liveReviewService) {
        this.liveReviewService= liveReviewService;
    }

    @ApiOperation(value="thread 작성", notes="이 api는 comment review 하위에 thread를 저장합니다. thread는 discussion이 완료되어도 누구나 작성할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "새로운 thread 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("{reviewId}/complete")
    public ThreadResponseDto saveThread(@PathVariable("reviewId") Long reviewId, @RequestBody @Valid CreateThreadRequestDto requestDto) {
        User user = User.builder().id(1111L).build();
        ReviewThread reviewThread = reviewService.saveThread(user, requestDto, reviewId);
        return ThreadResponseDto.fromEntity(reviewThread);
    }
}
