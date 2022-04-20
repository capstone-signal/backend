package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.CommentReviewResponseDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateLiveReviewRequestDto;
import com.hidiscuss.backend.controller.dto.LiveReviewResponseDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/liveReview")
public class LiveReviewController {

    private final ReviewService reviewService;

    public LiveReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation(value="live review 완료", notes="이 api는 liveReview review를 저장합니다. requestBody로 온 live review diff도 개수만큼 테이블에 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "새로운 liveReview review 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("")
    public LiveReviewResponseDto saveLiveReview(@RequestParam("type") ReviewType reviewType, @RequestBody @Valid CreateLiveReviewRequestDto requestDto) {
        User user = User.builder().id(1111L).build();
        Review review = reviewService.saveLiveReview(user, requestDto, reviewType);
        review = reviewService.saveLiveReviewDiff(requestDto, review);
        return LiveReviewResponseDto.fromEntity(review);
    }
}
