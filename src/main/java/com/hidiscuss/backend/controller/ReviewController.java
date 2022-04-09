package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.CommentReviewResponseDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.controller.dto.ThreadResponseDto;
import com.hidiscuss.backend.entity.ReviewType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewController {

//    private final ReviewService reviewService;
//
//    public ReviewController(ReviewService reviewService) {
//        this.reviewService = reviewService;
//    }

    @ApiOperation(value="comment review 저장", notes="이 api는 comment review를 저장합니다. requestBody로 온 comment review diff도 개수만큼 테이블에 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "새로운 comment review 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("")
    public CommentReviewResponseDto saveCommentReview(@RequestParam("type") ReviewType reviewType, @RequestBody @Valid CreateCommentReviewRequestDto requestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //CommentReviewResponseDto responseDto = reviewService.saveReview(auth.getName(), requestDto, reviewType);
        //return responseDto;
        return CommentReviewResponseDto.fromEntity(CreateCommentReviewRequestDto.toEntity(requestDto));
    }

    @ApiOperation(value="thread 작성", notes="이 api는 comment review 하위에 thread를 저장합니다. thread는 discussion이 완료되어도 누구나 작성할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "새로운 thread 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @PostMapping("{reviewId}/thread")
    public ThreadResponseDto saveThread(@PathVariable("reviewId") Long reviewId, @RequestBody @Valid CreateThreadRequestDto requestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //ThreadResponseDto responseDto = reviewService.saveThread(auth.getName(), requestDto, reviewId);
        //return responseDto;
        return ThreadResponseDto.fromEntity(CreateThreadRequestDto.toEntity(requestDto));
    }
}
