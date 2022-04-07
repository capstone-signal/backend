package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.controller.dto.DiscussionResponseDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {
    //private final DiscussionService discussionService;

    //public DiscussionController(DiscussionService discussionService) {
    //    this.discussionService = discussionService;
    //}

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
            if (createDiscussionRequestDto.gitNodeId == null) {
                throw new IllegalArgumentException("gitNodeId가 없습니다.");
            }
        }
        return DiscussionResponseDto.fromEntity(CreateDiscussionRequestDto.toEntity(createDiscussionRequestDto));
        //discussionService.createDiscussion(createDiscussionRequestDto);
    }
}


