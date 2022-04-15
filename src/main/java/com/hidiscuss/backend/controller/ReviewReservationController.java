package com.hidiscuss.backend.controller;


import com.hidiscuss.backend.controller.dto.CreateReviewReservationRequestDto;
import com.hidiscuss.backend.controller.dto.ReviewReservationResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReviewReservationController {

    @ApiOperation(value ="discussion Id를 받아 이미 예약된 리뷰들 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "예약된 리뷰들 반환 (없는 경우 포함)"),
            @ApiResponse(code = 400, message = "discussionId가 null 또는 Discussion이 존재하지 않음."),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    //TODO: Authenticated Method
    @GetMapping("")
    public List<ReviewReservationResponseDto> getReviewReservationByDiscussionId(
            @RequestParam Long discussionId) {
       /* if (discussionService.findById(discussionId) == null) {
            throw new IllegalArgumentException("discussionId is not exist");
        }*/
        //reviewReservationService.findByDiscussionId(discussionId);
        return null;
    }

    @ApiOperation(value = "새로운 라이브 리뷰 예약 추가")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 400, message ="잘못된 discussionId 또는 이미 예약된 시간"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @ResponseStatus(HttpStatus.CREATED)
    //TODO: Authenticated Method
    @PostMapping("")
    public ReviewReservationResponseDto addReviewReservation(
            @RequestBody @Valid CreateReviewReservationRequestDto createReviewResevationRequestDto
    ) {
        return null;
    }
}
