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
    private ReviewDto review;
    private DiscussionResponseDto discussion;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="Asia/Seoul")
    private ZonedDateTime reviewStartDateTime;
    private Boolean reviewerParticipated;
    private Boolean revieweeParticipated;

    private ReviewReservationResponseDto(Long id, DiscussionResponseDto discussion, ZonedDateTime reviewStartDateTime, Boolean reviewerParticipated, Boolean revieweeParticipated, Boolean isdone, User user) {

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
                ReviewDto.fromEntity(reviewReservation.getReview()));
    }
}
