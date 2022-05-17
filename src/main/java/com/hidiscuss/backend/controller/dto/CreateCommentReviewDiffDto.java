package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentReviewDiffDto {

    @NotNull
    public DiscussionCodeDto discussionCode;

    @NotNull
    public String codeAfter;

    @NotNull
    public List<Long> codeLocate = new ArrayList<>();

    @Nullable
    public String comment;

    public static CommentReviewDiff toEntity(CreateCommentReviewDiffDto dto, Review review, DiscussionCode code) {
        return CommentReviewDiff.builder()
                .review(review)
                .discussionCode(code)
                .codeAfter(dto.getCodeAfter())
                .codeLocate(getCodeLocateString(dto.codeLocate))
                .comment(dto.getComment())
                .build();
    }

    public static CreateCommentReviewDiffDto fromEntity(CommentReviewDiff entity) {
        CreateCommentReviewDiffDto dto = new CreateCommentReviewDiffDto();
        dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
        dto.codeAfter = entity.getCodeAfter();
        String[] locate = entity.getCodeLocate().split(",");
        dto.codeLocate = List.of(Long.parseLong(locate[0]), Long.parseLong(locate[1]));
        dto.comment = entity.getComment();

        return dto;
    }

    public static String getCodeLocateString(List<Long> codeLocate) {
        return codeLocate.get(0).toString() + "," + codeLocate.get(1).toString();
    }
}
