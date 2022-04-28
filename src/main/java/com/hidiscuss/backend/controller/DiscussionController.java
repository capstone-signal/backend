package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.*;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.service.DiscussionCodeService;
import com.hidiscuss.backend.service.DiscussionService;
import com.hidiscuss.backend.service.ReviewService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/discussion")
@AllArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;
    private final DiscussionCodeService discussionCodeService;


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
    public DiscussionDetailResponseDto getDiscussion(@PathVariable("discussionId") Long discussionId) {
        // UserAuthority: 어떤 유저인지에 따라 다르게 동작해야 하는 페이지
        // 사용자가 라이브 리뷰 예약한 사람이라면, 버튼 클릭했을 때 Picker 시간 확인해서 각자 다른 모달을 보여줌
        // 즉 진입 시점을 봐야 함,,, 그니까 리뷰 예약 리스트를 보고, 현재 시간이 있는지 보고, 있다면 거기의 리뷰어를 가져옴
        Discussion discussion = discussionService.findByIdFetchOrNull(discussionId);
        List<DiscussionCode> discussionCodeList = discussionCodeService.getDiscussionCode(discussion);
        return new DiscussionDetailResponseDto(DiscussionResponseDto.fromEntity(discussion), DiscussionCodeDto.fromEntityList(discussionCodeList), 0L);
    }
}


