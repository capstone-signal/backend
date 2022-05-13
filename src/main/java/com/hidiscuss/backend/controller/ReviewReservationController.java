package com.hidiscuss.backend.controller;


import com.hidiscuss.backend.config.SecurityConfig;
import com.hidiscuss.backend.controller.dto.CompleteLiveReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateReviewReservationRequestDto;
import com.hidiscuss.backend.controller.dto.LiveReviewDiffResponseDto;
import com.hidiscuss.backend.controller.dto.ReviewReservationResponseDto;
=import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import com.hidiscuss.backend.repository.ReviewRepository;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.service.DiscussionService;
import com.hidiscuss.backend.service.LiveReviewDiffService;
import com.hidiscuss.backend.service.ReviewReservationService;
import com.hidiscuss.backend.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReviewReservationController {

    private final DiscussionService discussionService;
    private final ReviewReservationService reviewReservationService;
    private final LiveReviewDiffService liveReviewDiffService;
    private final LiveReviewDiffRepository liveReviewDiffRepository;

    @GetMapping("")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "discussion Id를 받아 이미 예약된 리뷰들 반환")
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
            @ApiResponse(code = 400, message = "잘못된 discussionId 또는 이미 예약된 시간"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ReviewReservationResponseDto addReviewReservation(
            @RequestBody @Valid CreateReviewReservationRequestDto createReviewReservationRequestDto
            , @AuthenticationPrincipal String userId
    ) {
        Discussion discussion = discussionService.findByIdOrNull(createReviewReservationRequestDto.discussionId);
        User user = userService.findById(Long.parseLong(userId));
        if (discussion == null) { // 존재하지 않는 Discussion
            throw NotFoundDiscussion();
        }
        if (!discussion.getLiveReviewRequired()) {
            throw new IllegalArgumentException("Live Review is not required");
        }
        ReviewReservation reviewReservation = reviewReservationService.create(createReviewReservationRequestDto.reviewStartDateTime, discussion, user);
        return ReviewReservationResponseDto.fromEntity(reviewReservation);
    }

    @ApiOperation(value = "UserId, DiscussionId, Reservation time을 받아 이미 예약된 리뷰들 반환")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "내가 Reivewee 혹은 Reviewer 이고 24시간 이내의 reservation 이 있으면 True, 없으면 False"),
            @ApiResponse(code = 400, message = "discussionId가 null 또는 Discussion이 존재하지 않음."),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("my")
    public List<ReviewReservationResponseDto> isReservedReservation(@AuthenticationPrincipal Long userId) {
        List<ReviewReservation> reviewReservationList = reviewReservationService.findByDiscussionIdAndUserId(userId);
        return reviewReservationList.stream().map(ReviewReservationResponseDto::fromEntity).collect(Collectors.toList());
    }

    @PostMapping("participate/{reservationId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "User가 참여 버튼을 누르면 discussion, Particioate 상태 변경 후 Review가 없으면 만들어서 반환")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰 참여 버튼을 누르면 유저인지 확인하고 새로운 리뷰를 만들어 리뷰를 반환해준다."),
            @ApiResponse(code = 400, message = "ReviewReservationID가 null 또는 reviewreservation이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public ReviewReservationResponseDto enterLiveReviewReservation(@PathVariable("reservationId") Long reservationId, @AuthenticationPrincipal Long userId, @RequestBody List<Long> discussionCodesIdList) {
        ReviewReservation reviewReservation = reviewReservationService.findByIdOrNull(reservationId);
        if (reviewReservation == null) {
            throw NotFoundReservaiton();
        }
        if(!Objects.equals(reviewReservation.getReviewer().getId(), userId) && !Objects.equals(reviewReservation.getDiscussion().getUser().getId(), userId)){
            throw  NoReviewerOrReviewee();
        }
        if(reviewReservation.getReviewer().getId().equals(userId)){
            reviewReservation.setReviewerParticipated(true);
        } else{
            reviewReservation.setRevieweeParticipated(true);
        }

        if (reviewReservation.getReview() == null){
          reviewReservation = reviewReservationService.createNewLiveReviewAndDiffs(discussionCodesIdList,reviewReservation);
        }
        return ReviewReservationResponseDto.fromEntity(reviewReservation);
    }

    @PutMapping("review/livediff/{diffId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "N분마다 포커싱 되어 있는 파일 업데이트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰 참여 버튼을 누르면 유저인지 확인하고 새로운 리뷰를 만들어 리뷰를 반환해준다."),
            @ApiResponse(code = 400, message = "ReviewReservationID가 null 또는 reviewreservation이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public LiveReviewDiffResponseDto updateFocusedDiff(@PathVariable("diffId") Long diffId, @RequestBody String codeAfter) {
        LiveReviewDiff liveReviewDiff = liveReviewDiffRepository.findById(diffId).orElseThrow(this::NotFoundLiveDiff);

        liveReviewDiff = liveReviewDiffService.updateDiff(liveReviewDiff,codeAfter);
        return LiveReviewDiffResponseDto.fromEntity(liveReviewDiff);
    }

    @PutMapping("review/complete/{reviewReservationId}")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "라이브리뷰 완료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰완료 버튼을 누르거나 한시간이 되면 완료시키는 Api"),
            @ApiResponse(code = 400, message = "ReviewReservationID가 null 또는 reviewreservation이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public Map<String, Long> completeLiveReview(@PathVariable("reviewReservationId") Long reservationId, @RequestBody CompleteLiveReviewRequestDto completeLiveReviewRequestDto) {
        ReviewReservation reviewReservation = reviewReservationService.findByIdOrNull(reservationId);
        reviewReservation.setIsdone(Boolean.TRUE);
        if(reviewReservation.getDiscussion().getState() == DiscussionState.NOT_REVIEWED) {
            reviewReservation.getDiscussion().setState(DiscussionState.REVIEWING);
        }
        reviewReservationService.saveAllDiffs(completeLiveReviewRequestDto);
        HashMap<String, Long> map = new HashMap<>();
        map.put("discsussionId",reviewReservation.getDiscussion().getId());
        map.put("reservationId",reviewReservation.getId());
        return map;
    }


    private RuntimeException NoReviewerOrReviewee() {
        return new IllegalArgumentException("You are not Reviewee Or Reviewer");
    }
    private RuntimeException NotFoundReservaiton() {
        return new IllegalArgumentException("Reservation not found");
    }
    private RuntimeException NotFoundLiveDiff() {
        return new IllegalArgumentException("LiveDiff not found");
    }
    private RuntimeException NotFoundDiscussion() {
        return new IllegalArgumentException("Discussion not found");
    }
}
