package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionState;
import com.hidiscuss.backend.entity.LiveReviewAvailableTimes;
import com.hidiscuss.backend.entity.User;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class CreateDiscussionRequestDto {
    @NotNull
//    @Size(min = 1, max = 1234)
    public String question;

    @NotNull
    public boolean liveReviewRequired = false;

    @NotNull
    @Pattern(regexp = "^(PR|COMMIT|DIRECT)$")
    public String discussionType;

    @Nullable
    public String gitRepositoryId;
    @Nullable
    public String gitNodeId;

    @Nullable
    public LiveReviewAvailableTimes liveReviewAvailableTimes;

    @Nullable
    public boolean usePriority = false;

    @NotNull
    public List<Long> tagIds;

    @Nullable
    public List<CreateDiscussionCodeRequestDto> codes;

    public static Discussion toEntity(CreateDiscussionRequestDto dto, User user) {
        return Discussion.builder()
                .question(dto.question)
                .user(user)
                .liveReviewRequired(dto.liveReviewRequired)
                .liveReviewAvailableTimes(dto.liveReviewAvailableTimes)
                .priority(dto.usePriority ? 255L : 0L)
                .state(DiscussionState.NOT_REVIEWED)
                .build();
    }

    public boolean isDirectDiscussion() {
        return this.discussionType.equals("DIRECT");
    }
}
