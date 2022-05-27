package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CommentReviewDiffResponseDto extends ReviewDiffResponseDto {
    @NotNull
    public List<Long> codeLocate;

    @Nullable
    public String comment;

    public static CommentReviewDiffResponseDto fromEntity(CommentReviewDiff entity) {
        CommentReviewDiffResponseDto dto = new CommentReviewDiffResponseDto();
        dto.setBaseResponse(entity);
        dto.id = entity.getId();
        dto.discussionCode = entity.getDiscussionCode().getId();
        dto.codeAfter = entity.getCodeAfter();
        String[] locate = entity.getCodeLocate().split(",");
        dto.codeLocate = List.of(Long.parseLong(locate[0]), Long.parseLong(locate[1]));
        dto.comment = entity.getComment();
        return dto;
    }

    public static List<CommentReviewDiffResponseDto> fromEntityList(List<CommentReviewDiff> entityList) {
        List<CommentReviewDiffResponseDto> dtos = new ArrayList<>();

        for(CommentReviewDiff entity:entityList){
            CommentReviewDiffResponseDto dto = new CommentReviewDiffResponseDto();
            dto.setBaseResponse(entity);
            dto.id = entity.getId();
            dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
            dto.codeAfter = entity.getCodeAfter();
            String[] locate = entity.getCodeLocate().split(",");
            dto.codeLocate = List.of(Long.parseLong(locate[0]), Long.parseLong(locate[1]));
            dto.comment = entity.getComment();
            dtos.add(dto);
        }
        return dtos;
    }
}
