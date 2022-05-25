package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.ReviewDiff;

import java.util.ArrayList;
import java.util.List;

public class GetReviewDiffsResponseDto extends ReviewDiffResponseDto {

    public static List<GetReviewDiffsResponseDto> fromEntityCommentDiffList(List<CommentReviewDiff> commentReviewDiffList) {
        List<GetReviewDiffsResponseDto> dtoList = new ArrayList<>();

        for (CommentReviewDiff diff : commentReviewDiffList) {
            GetReviewDiffsResponseDto dto = new GetReviewDiffsResponseDto();
            dto.id = diff.getId();
            dto.codeAfter = diff.getCodeAfter();
            dto.discussionCode = DiscussionCodeDto.fromEntity(diff.getDiscussionCode());
            dtoList.add(dto);
        }

        return dtoList;

    }

    public static List<GetReviewDiffsResponseDto> fromEntityLiveDiffList(List<LiveReviewDiff> liveReviewDiffList) {
        List<GetReviewDiffsResponseDto> dtoList = new ArrayList<>();

        for (LiveReviewDiff diff : liveReviewDiffList) {
            GetReviewDiffsResponseDto dto = new GetReviewDiffsResponseDto();
            dto.id = diff.getId();
            dto.codeAfter = diff.getCodeAfter();
            dto.discussionCode = DiscussionCodeDto.fromEntity(diff.getDiscussionCode());
            dtoList.add(dto);
        }

        return dtoList;
    }
}
