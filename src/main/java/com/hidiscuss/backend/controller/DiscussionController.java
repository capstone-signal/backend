package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.*;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.service.DiscussionCodeService;
import com.hidiscuss.backend.service.DiscussionService;
import com.hidiscuss.backend.service.ReviewService;
import com.hidiscuss.backend.utils.PageRequest;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Discussion discussion = discussionService.findByIdFetchOrNull(discussionId);
        List<DiscussionCode> discussionCodeList = discussionCodeService.getDiscussionCode(discussion);

        return new DiscussionDetailResponseDto(DiscussionResponseDto.fromEntity(discussion), DiscussionCodeDto.fromEntityList(discussionCodeList));
    }

    @ApiOperation(value = "Discussion 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Discussion 목록 조회"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    @GetMapping("/")
    public Page<DiscussionResponseDto> getDiscussions(@RequestParam("page") int page
            , @RequestParam(value = "state", required = false, defaultValue = "not_reviewed") DiscussionState state
            , @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
            , @RequestParam(value = "tags", required = false, defaultValue = "") List<String> tags
            , @RequestParam(value = "onlymine", required = false, defaultValue = "0") boolean onlymine
            , @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort) {
        PageRequest pageRequest = new PageRequest(page, Sort.by(sort).descending());
        GetDiscussionsDto dto = new GetDiscussionsDto(state, keyword, tags);
        User user = User.builder().id(7000L).build();
        if (onlymine)
            dto.setUserId(user.getId());
        Page<Discussion> entities = discussionService.getDiscussionsFiltered(dto, pageRequest.of());

        return entities.map(i -> DiscussionResponseDto.fromEntity(i));
    }
}


