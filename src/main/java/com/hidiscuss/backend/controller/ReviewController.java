package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.entity.ReviewType;
import com.hidiscuss.backend.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 사용자 정보 추가, api response 예제 찾아보기, responseEntity, dao 어떻게 사용하는지?
    @ApiOperation(value="comment review 저장", notes="이 api는 comment review를 저장합니다. requestBody로 온 comment review diff도 개수만큼 테이블에 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("")
    public ResponseEntity<> saveCommentReview(@RequestParam("type") ReviewType reviewType) {
        reviewService.saveReview(userId, content, reviewType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value="thread 작성", notes="이 api는 comment review 하위에 thread를 저장합니다. thread는 discussion이 완료되어도 누구나 작성할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "API 정상 작동"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("{reviewId}/thread")
    public ResponseEntity<> saveThread(@PathVariable("reviewId") Long reviewId) {
        reviewService.saveThread(userId, content, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
