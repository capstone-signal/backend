package com.hidiscuss.backend.controller;


import com.hidiscuss.backend.config.SecurityConfig;
import com.hidiscuss.backend.controller.dto.CreateReviewReservationRequestDto;
import com.hidiscuss.backend.controller.dto.ReviewReservationResponseDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.hidiscuss.backend.service.DiscussionService;
import com.hidiscuss.backend.service.ReviewReservationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReviewReservationController {

    private final DiscussionService discussionService;
    private final ReviewReservationService reviewReservationService;

    @GetMapping("")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value ="discussion Id를 받아 이미 예약된 리뷰들 반환")
    @ApiResponses({
            @ApiResponse(code = 200, message = "예약된 리뷰들 반환 (없는 경우 포함)"),
            @ApiResponse(code = 400, message = "discussionId가 null 또는 Discussion이 존재하지 않음."),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public List<ReviewReservationResponseDto> getReviewReservationByDiscussionId(
            @RequestParam Long discussionId) {
        if (discussionService.findByIdOrNull(discussionId) == null) {
            throw NotFoundDiscussion();
        }
        List<ReviewReservation> reviewReservations = reviewReservationService.findByDiscussionId(discussionId);
        return reviewReservations.stream().map(ReviewReservationResponseDto::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "새로운 라이브 리뷰 예약 추가")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 400, message ="잘못된 discussionId 또는 이미 예약된 시간"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ReviewReservationResponseDto addReviewReservation(
            @RequestBody @Valid CreateReviewReservationRequestDto createReviewReservationRequestDto
    ) {
        Discussion discussion = discussionService.findByIdOrNull(createReviewReservationRequestDto.discussionId);
        if (discussion == null) { // 존재하지 않는 Discussion
            throw NotFoundDiscussion();
        }
        if (!discussion.getLiveReviewRequired()) {
            throw new IllegalArgumentException("Live Review is not required");
        }
        ReviewReservation reviewReservation = reviewReservationService.create(createReviewReservationRequestDto.reviewStartDateTime, discussion);
        return ReviewReservationResponseDto.fromEntity(reviewReservation);
    }

    private RuntimeException NotFoundDiscussion() {
        return new IllegalArgumentException("Discussion not found");
    }
}
