package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.config.SecurityConfig;
import com.hidiscuss.backend.controller.dto.*;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.service.*;
import com.hidiscuss.backend.utils.ApiPageable;
import com.hidiscuss.backend.utils.PageRequest;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
//    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "Discussion 생성")
    @ApiResponses({
            @ApiResponse(code = 201, message = "새로운 Discussion 생성 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public DiscussionResponseDto createDiscussion(
            @RequestBody CreateDiscussionRequestDto createDiscussionRequestDto
            , @AuthenticationPrincipal User user) {
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

//        if (createDiscussionRequestDto.usePriority) {
//             rewardService.checkReward(createDiscussionRequestDto.rewardId) Transaction
//        }
        Discussion discussion = discussionService.create(createDiscussionRequestDto, user);
        return DiscussionResponseDto.fromEntity(discussion);
    }

    @GetMapping("/{discussionId}")
    @ApiOperation(value = "Discussion 상세페이지 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Discussion 상세페이지 조회"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public DiscussionDetailResponseDto getDiscussion(@PathVariable("discussionId") Long discussionId) {
        Discussion discussion = discussionService.findByIdFetchOrNull(discussionId);
        List<DiscussionCode> discussionCodeList = discussionCodeService.getDiscussionCode(discussion);

        return new DiscussionDetailResponseDto(DiscussionResponseDto.fromEntity(discussion), DiscussionCodeDto.fromEntityList(discussionCodeList));
    }

    @GetMapping("/")
    @ApiPageable
    @ApiOperation(value = "Discussion 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Discussion 목록 조회"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public Page<DiscussionResponseDto> getDiscussions(@Valid GetDiscussionsDto dto
            , @ApiIgnore @PageableDefault(sort = "createdAt") Pageable pageable
            , @AuthenticationPrincipal User user) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getSort());
        if (dto.getOnlyMine())
            dto.setUserId(user.getId());
        Page<Discussion> entities = discussionService.getDiscussionsFiltered(dto, pageRequest.of());

        return entities.map(DiscussionResponseDto::fromEntity);
    }

    @DeleteMapping("/{discussionId}")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "Discussion 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Discussion 삭제"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public Long deleteDiscussion(@PathVariable("discussionId") Long discussionId
            , @AuthenticationPrincipal User user) {
        Discussion discussion = discussionService.findByIdFetchOrNull(discussionId);
        return discussionService.delete(discussion, user);
    }

    @PutMapping("/{discussionId}/complete")
    @Secured(SecurityConfig.DEFAULT_ROLE)
    @ApiOperation(value = "Discussion 완료")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Discussion 완료"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public Long completeDiscussion(@PathVariable("discussionId") Long discussionId
            , @AuthenticationPrincipal User user) {
        Discussion discussion = discussionService.findByIdFetchOrNull(discussionId);
        return discussionService.complete(discussion, user);
    }
}


