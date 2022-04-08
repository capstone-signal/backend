package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewThread;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CreateCommentReviewRequestDto {
    // review를 만들기 위한 정보
    @NotNull
    public Long discussiondId;

    // commentReviewDiff를 만들기 위한 정보, review가 만들어진 다음에 채워짐
    public Long reviewId;

    @NotNull
    public List<CreateCommentReviewDiffRequestDto> diffList;

    public static Review toEntity(CreateCommentReviewRequestDto dto) {
        return Review.builder()
                //.review(dto.reviewId)
                //.discussion(dto.discussiondId)
                .build();
    }
}
