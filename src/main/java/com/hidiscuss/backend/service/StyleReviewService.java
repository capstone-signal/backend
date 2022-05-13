package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateCommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.DiscussionCodeDto;
import com.hidiscuss.backend.entity.DiscussionCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StyleReviewService {
    public List<CreateCommentReviewDiffDto> createStyleReviewDto(List<DiscussionCode> codes) {
        // add jython codes
        List<CreateCommentReviewDiffDto> dto = codes.stream()
                .map((i) -> CreateCommentReviewDiffDto
                        .builder()
                        .discussionCode(DiscussionCodeDto.fromEntity(i))
                        .comment("pypy")
                        .codeAfter("afaf")
                        .codeLocate("lolo")
                        .build()
                ).collect(Collectors.toList());
        return dto;
    }
}
