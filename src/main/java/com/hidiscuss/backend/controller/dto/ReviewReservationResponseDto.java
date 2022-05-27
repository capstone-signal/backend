package com.hidiscuss.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.hidiscuss.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ReviewReservationResponseDto {

    private Long id;
    private UserResponseDto reviewer;
    private ReviewResponseDto review;
    private DiscussionResponseDto discussion;
    private ZonedDateTime reviewStartDateTime;
    private Boolean reviewerParticipated;
    private Boolean revieweeParticipated;

    private ReviewReservationResponseDto(Long id, DiscussionResponseDto discussion, ZonedDateTime reviewStartDateTime, Boolean reviewerParticipated, Boolean revieweeParticipated, User user, ReviewResponseDto review ) {
        this.id = id;
        this.discussion = discussion;
        this.reviewStartDateTime = reviewStartDateTime;
        this.reviewerParticipated = reviewerParticipated;
        this.revieweeParticipated = revieweeParticipated;
        this.reviewer = UserResponseDto.fromEntity(user);
        this.review = review;
    }

    public static ReviewReservationResponseDto fromEntity(ReviewReservation reviewReservation) {
        return new ReviewReservationResponseDto(
                reviewReservation.getId(),
                DiscussionResponseDto.fromEntity(reviewReservation.getDiscussion()),
                reviewReservation.getReviewStartDateTime(),
                reviewReservation.getReviewerParticipated(),
                reviewReservation.getRevieweeParticipated(),
                reviewReservation.getReviewer(),
                ReviewResponseDto.fromEntity(reviewReservation.getReview()));
    }
}
