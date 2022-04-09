package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.controller.dto.DiscussionResponseDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.service.DiscussionService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/discussion")
@AllArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;


//    @PostMapping("/")
//    @ResponseStatus(HttpStatus.CREATED)
//    @ApiOperation(value = "Discussion 생성")
//    @ApiResponses({
//            @ApiResponse(code = 201, message = "새로운 Discussion 생성 성공"),
//            @ApiResponse(code = 400, message = "잘못된 요청"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public DiscussionResponseDto createDiscussion(
//            @RequestBody @Valid CreateDiscussionRequestDto createDiscussionRequestDto) {
//        if (createDiscussionRequestDto.isDirectDiscussion()) {
//            if (createDiscussionRequestDto.codes == null) {
//                throw new IllegalArgumentException("코드가 없습니다.");
//            }
//        } else {
//            if (createDiscussionRequestDto.gitNodeId == null || createDiscussionRequestDto.gitRepositoryId == null) {
//                throw new IllegalArgumentException("gitNodeId 또는 gitRepositoryId가 없습니다.");
//            }
//        }
//
//        if (createDiscussionRequestDto.liveReviewRequired) {
//            if (createDiscussionRequestDto.liveReviewAvailableTimes == null) {
//                throw new IllegalArgumentException("liveReviewAvailableTimes가 없습니다.");
//            }
//        }
//
//        if (createDiscussionRequestDto.usePriority) {
//            // rewardService.checkReward(createDiscussionRequestDto.rewardId) Transaction
//        }
//        // TODO : Inject the Authenticated User
//        User user = User.builder().id(9999L).build();
//        Discussion discussion = discussionService.create(createDiscussionRequestDto, user);
//        return DiscussionResponseDto.fromEntity(discussion);
//    }
}


