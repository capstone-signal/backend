package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.SendEmailDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.LiveReviewAvailableTimes;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.exception.UserAuthorityException;
import com.hidiscuss.backend.repository.ReviewReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewReservationService {
    public static int REVIEW_SESSION_DURATION_IN_HOURS = 1;

    private final ReviewReservationRepository reviewReservationRepository;
    private final EmailService emailService;
    private final ReviewService reviewService;

    public List<ReviewReservation> findByDiscussionId(Long discussionId) {
        return reviewReservationRepository.findByDiscussionId(discussionId);
    }

    public List<ReviewReservation> findByUserId(Long userId) {
        return reviewReservationRepository.findByUserId(userId);
    }

    public ReviewReservation findByReservationId(Long resrvationId) {
        return reviewReservationRepository.findByReservationId(resrvationId);
    }

    public ReviewReservation create(
            ZonedDateTime startTime,
            Discussion discussion,
            User reviewer
    ) {
        Long discussionId = discussion.getId();
        if (discussion.getUser().getId().equals(reviewer.getId()))
            throw new UserAuthorityException("Cannot schedule a review for your own discussion");
        List<ReviewReservation> alreadyExistReviewReservation = this.findByDiscussionId(discussionId);
        alreadyExistReviewReservation.forEach(reviewReservation -> {
            if(isDuplicatedReviewReservation(startTime, reviewReservation)) {
                throw new IllegalArgumentException("이미 예약된 시간입니다.");
            }
        });

        LiveReviewAvailableTimes liveReviewAvailableTimes = discussion.getLiveReviewAvailableTimes();

        boolean isAvailableInsert = liveReviewAvailableTimes.getTimes().stream().anyMatch(timeRange -> {
          ZonedDateTime start = timeRange.getStart();
          ZonedDateTime end = timeRange.getEnd();
          return start.isBefore(startTime) && end.isAfter(startTime);
        });

        if(!isAvailableInsert) {
            throw new IllegalArgumentException("예약 가능한 시간이 아닙니다.");
        }

        ReviewReservation reviewReservation = ReviewReservation.builder()
                .reviewStartDateTime(startTime.minusSeconds(1))
                .discussion(discussion)
                .reviewer(reviewer)
                .build();


        Review review = reviewService.createLiveReview(reviewReservation);

        String revieweeEmail = discussion.getUser().getEmail();
        String reviewerEmail = reviewReservation.getReviewer().getEmail();

        emailService.send(new SendEmailDto(revieweeEmail, getSubject(), getContent(startTime)));
        emailService.send(new SendEmailDto(reviewerEmail, getSubject(), getContent(startTime)));

        reviewReservation.setReview(review);
        if (discussion.getState().equals(DiscussionState.NOT_REVIEWED))
        {
            discussion.setState(DiscussionState.REVIEWING);
        }
        return reviewReservationRepository.save(reviewReservation);
    }

    private String getSubject() {
        return "[Hidiscuss] 라이브 리뷰 예약 완료";
    }

    private String getContent(ZonedDateTime zonedDateTime) {
        StringBuilder sb = new StringBuilder();
        // utc to asia/seoul
        //ZonedDateTime zonedDateTime = ZonedDateTime.of(reviewStartTime, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        String reviewStartTimeStr = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분"));
        sb.append("<h1>예약이 완료되었습니다.</h1>");
        sb.append("<p>예약이 완료되었습니다.</p>");
        sb.append("<p>예약 시간 : ").append(reviewStartTimeStr).append("</p>");
        return sb.toString();
    }

    private boolean isDuplicatedReviewReservation(ZonedDateTime startTime, ReviewReservation reviewReservation) {
        ZonedDateTime comparerStartTime = reviewReservation.getReviewStartDateTime();
        ZonedDateTime comparerEndTime = comparerStartTime.plusHours(REVIEW_SESSION_DURATION_IN_HOURS);
        boolean isEqual = comparerStartTime.isEqual(startTime);
        boolean isBetween = comparerStartTime.isBefore(startTime) && comparerEndTime.isAfter(startTime);
        boolean isStartBeforeHour = startTime.isAfter(comparerStartTime.minusHours(REVIEW_SESSION_DURATION_IN_HOURS)) && startTime.isBefore(comparerStartTime);
        return isEqual || isBetween || isStartBeforeHour;
    }



    public ReviewReservation findByIdOrNull(Long reservationId) {
            return reviewReservationRepository.findById(reservationId).orElse(null);
    }

    public ReviewReservation checkUser(ReviewReservation reviewReservation,Long userId){
        if(reviewReservation.getReviewer().getId().equals(userId)){
            reviewReservation.setReviewerParticipated(true);
        } else{
            reviewReservation.setRevieweeParticipated(true);
        }
        return reviewReservation;
    }
}
