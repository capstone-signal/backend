package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.ReviewReservation;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateReviewReservationRequestDto {
    @NotNull
    public Long discussionId;

    @NotNull
    @Future
    public LocalDateTime reviewDate;
}
