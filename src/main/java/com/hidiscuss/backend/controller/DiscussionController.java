package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.controller.dto.DiscussionDetailResponseDto;
import com.hidiscuss.backend.controller.dto.DiscussionResponseDto;
import com.hidiscuss.backend.controller.dto.ReviewDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.service.DiscussionService;
import com.hidiscuss.backend.service.ReviewService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/discussion")
@AllArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;
    private final ReviewService reviewService;


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Discussion 생성")
    @ApiResponses({
            @ApiResponse(code = 201, message = "새로운 Discussion 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public DiscussionResponseDto createDiscussion(
            @RequestBody @Valid CreateDiscussionRequestDto createDiscussionRequestDto) {
        if (createDiscussionRequestDto.isDirectDiscussion()) {
            if (createDiscussionRequestDto.codes == null) {
                throw new IllegalArgumentException("코드가 없습니다.");
            }
        } else {
            if (createDiscussionRequestDto.gitNodeId == null || createDiscussionRequestDto.gitRepositoryId == null) {
                throw new IllegalArgumentException("gitNodeId 또는 gitRepositoryId가 없습니다.");
            }
        }

        if (createDiscussionRequestDto.liveReviewRequired) {
            if (createDiscussionRequestDto.liveReviewAvailableTimes == null) {
                throw new IllegalArgumentException("liveReviewAvailableTimes가 없습니다.");
            }
        }

        if (createDiscussionRequestDto.usePriority) {
            // rewardService.checkReward(createDiscussionRequestDto.rewardId) Transaction
        }
        // TODO : Inject the Authenticated User
        User user = User.builder().id(9999L).build();
        Discussion discussion = discussionService.create(createDiscussionRequestDto, user);
        return DiscussionResponseDto.fromEntity(discussion);
    }

    @ApiOperation(value = "Discussion 상세페이지 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Discussion 상세페이지 조회"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/{discussionId}")
    public DiscussionDetailResponseDto getDiscussion(@PathVariable("discussionId") Long discussionId, Pageable pageable) {
        Discussion discussion = discussionService.findByIdFetchOrNull(discussionId);
        Page<ReviewDto> reviewResponseDtoPage = reviewService.findAllByDiscussionIdFetch(discussionId, pageable);
        return DiscussionDetailResponseDto.builder()
                .discussionResponseDto(DiscussionResponseDto.fromEntity(discussion))
                .reviewResponseDtoPage(reviewResponseDtoPage)
                .build();
        // pageUtil 만들기
        // dto 만들기
            // discussion 정보 -> discussionService
                // liveReview 예약 기능 구현 (reserved, disabled, available 상태는 구분)
            // review page -> reviewService, reviewDiffService
                // commentReviewDiff
                // liveReviewDiff
                // 새 리뷰 페이지 불러오는 api는 따로 reviewService에 만들기
            // user 정보
                // 어떤 유저인지에 따라 다르게 동작해야 하는 페이지
    }
}


