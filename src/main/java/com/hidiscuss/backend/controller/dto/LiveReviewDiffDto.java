package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiveReviewDiffDto {

        @NotNull
        public DiscussionCodeDto discussionCode;

        @NotNull
        public String codeAfter;

        @NotNull
        public String codeLocate;

        public static LiveReviewDiff toEntity(com.hidiscuss.backend.controller.dto.CommentReviewDiffDto dto, Review review, DiscussionCode code) {
            return LiveReviewDiff.builder()
                    .review(review)
                    .discussionCode(code)
                    .codeAfter(dto.getCodeAfter())
                    .codeLocate(dto.getCodeLocate())
                    .build();
        }

        public static com.hidiscuss.backend.controller.dto.LiveReviewDiffDto fromEntity(LiveReviewDiff entity) {
            com.hidiscuss.backend.controller.dto.LiveReviewDiffDto dto = new com.hidiscuss.backend.controller.dto.LiveReviewDiffDto();
            dto.discussionCode = DiscussionCodeDto.fromEntity(entity.getDiscussionCode());
            dto.codeAfter = entity.getCodeAfter();
            dto.codeLocate = entity.getCodeLocate();

            return dto;
        }

}

