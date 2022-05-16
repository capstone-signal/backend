package com.hidiscuss.backend.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Getter
public class CompleteLiveReviewResponseDto {

    @NotNull
    public HashMap<String, Long> discussionAndReviewReservationId;

    public static CompleteLiveReviewResponseDto fromIds(Long reviewReservationId, Long disscussionId){
        CompleteLiveReviewResponseDto dto = new CompleteLiveReviewResponseDto();
        dto.discussionAndReviewReservationId = new HashMap<>();
        dto.discussionAndReviewReservationId.put("discsussionId",disscussionId);
        dto.discussionAndReviewReservationId.put("reservationId",reviewReservationId);

        return dto;
    }
}
