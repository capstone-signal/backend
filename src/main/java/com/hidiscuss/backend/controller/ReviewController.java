package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.config.SecurityConfig;
import com.hidiscuss.backend.controller.dto.*;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.service.LiveReviewDiffService;
import com.hidiscuss.backend.service.ReviewReservationService;
import com.hidiscuss.backend.service.ReviewService;
import com.hidiscuss.backend.utils.ApiPageable;
import com.hidiscuss.backend.utils.PageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.hibernate.engine.internal.CacheHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.Id;
import javax.swing.text.StyledEditorKit;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final LiveReviewDiffService liveReviewDiffService;
    private final ReviewReservationService reviewReservationService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value="comment review 생성", notes="이 api는 comment review를 생성합니다. requestBody로 온 comment review diff도 개수만큼 테이블에 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "새로운 comment review 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public CommentReviewResponseDto saveCommentReview(@RequestParam("type") ReviewType reviewType
            , @RequestBody @Valid CreateCommentReviewRequestDto requestDto
            , @AuthenticationPrincipal String userId) {
        User user = User.builder().id(Long.parseLong(userId)).build();
        Review review = reviewService.createCommentReview(user, requestDto, reviewType);
        return CommentReviewResponseDto.fromEntity(review);
    }

    @PostMapping("{reviewId}/thread")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value="thread 작성", notes="이 api는 comment review 하위에 thread를 저장합니다. thread는 discussion이 완료되어도 누구나 작성할 수 있습니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "새로운 thread 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ThreadResponseDto saveThread(@PathVariable("reviewId") Long reviewId
            , @RequestBody @Valid CreateThreadRequestDto requestDto
            , @AuthenticationPrincipal String userId) {
        User user = User.builder().id(Long.parseLong(userId)).build();
        Review review = reviewService.findByIdFetchOrNull(reviewId);
        ReviewThread reviewThread = reviewService.createThread(user, requestDto, review);
        return ThreadResponseDto.fromEntity(reviewThread);
    }

    @GetMapping("")
    @ApiPageable
    @ApiOperation(value="review page 가져오기", notes="이 api는 page 단위로 review를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "review page 조회 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public Page<ReviewResponseDto> getReviews(@RequestParam("discussionId") Long discussionId
            , @ApiIgnore @PageableDefault(sort = "createdAt") Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getSort());
        Page<Review> entityPage = reviewService.findAllByDiscussionIdFetch(discussionId, pageRequest.of());
        return entityPage.map(ReviewResponseDto::fromEntity);
    }

    @PutMapping("livediff/{diffId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "N분마다 포커싱 되어 있는 파일 업데이트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "N분마다 포커싱 되어있는 파일에 대한 liveReviewdiff를 업데이트 한다."),
            @ApiResponse(code = 400, message = "해당 LiveDiff가 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public Boolean updateFocusedDiff(@PathVariable("diffId") Long diffId, @RequestBody UpdateFocusedDiffRequestDto updateFocusedDiffRequestDto, @AuthenticationPrincipal String userId) {
        LiveReviewDiff liveReviewDiff = liveReviewDiffService.findByIdAndUpdateByCodeAfter(diffId,updateFocusedDiffRequestDto.codeAfter);
        if (!CheckUser(userId, liveReviewDiff.getReview().getReviewer(), liveReviewDiff.getReview().getDiscussion()))
            throw NoReviewerOrReviewee();
        return true;
    }

    @PutMapping("complete/{reviewReservationId}")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "라이브리뷰 완료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리뷰완료 버튼을 누르거나 한시간이 되면 완료시키는 Api"),
            @ApiResponse(code = 400, message = "ReviewReservationID가 null 또는 reviewreservation이 존재하지 않음"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    public CompleteLiveReviewResponseDto completeLiveReview(@PathVariable("reviewReservationId") Long reservationId, @AuthenticationPrincipal String userId) {
        ReviewReservation reviewReservation = reviewReservationService.findByIdOrNull(reservationId);
        if(!CheckUser(userId, reviewReservation.getReviewer(), reviewReservation.getDiscussion()))
            throw NoReviewerOrReviewee();
        reviewService.changeCompleteStates(reviewReservation);
        return CompleteLiveReviewResponseDto.fromIds(reviewReservation.getDiscussion().getId(),reviewReservation.getId());
    }

//    @GetMapping("diff")
//    @ApiPageable
//    @ApiOperation(value=".")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "review page 조회 성공"),
//            @ApiResponse(code = 400, message = "잘못된 요청"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public List<LiveReviewDiff> getDiff(@RequestParam("reviewId") Long reviewId) {
//
//    }

    private RuntimeException NoReviewerOrReviewee() {
        return new IllegalArgumentException("You are not Reviewee Or Reviewer");
    }

    private Boolean CheckUser(String userId, User reviewer, Discussion discussion){
        if(!Objects.equals(reviewer.getId(), Long.parseLong(userId)) && !Objects.equals(discussion.getUser().getId(), Long.parseLong(userId))){
            return false;
        }
        return true;
    }
}
