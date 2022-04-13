package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.ReviewReservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewReservationResponseDto {

    private Long id;
    //private UserResponseDto user;
    //private ReviewResponseDto review;
    private DiscussionResponseDto discussion;
    private LocalDateTime reviewDate;
    private Boolean reviewerParticipated;
    private Boolean revieweeParticipated;
    private Boolean isdone;

    private ReviewReservationResponseDto(Long id, DiscussionResponseDto discussion, LocalDateTime reviewDate, Boolean reviewerParticipated, Boolean revieweeParticipated, Boolean isdone) {
        this.id = id;
        this.discussion = discussion;
        this.reviewDate = reviewDate;
        this.reviewerParticipated = reviewerParticipated;
        this.revieweeParticipated = revieweeParticipated;
        this.isdone = isdone;
        //this.user = user;
        //this.review = review;
    }

    public static ReviewReservationResponseDto fromEntity(ReviewReservation reviewReservation) {
        return new ReviewReservationResponseDto(
                reviewReservation.getId(),
                DiscussionResponseDto.fromEntity(reviewReservation.getDiscussion()),
                reviewReservation.getReviewDate(),
                reviewReservation.getReviewerParticipated(),
                reviewReservation.getRevieweeParticipated(),
                reviewReservation.getIsdone()
        );
    }
}
