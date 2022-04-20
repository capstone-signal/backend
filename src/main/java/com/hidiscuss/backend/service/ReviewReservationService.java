package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.LiveReviewAvailableTimes;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.hidiscuss.backend.repository.ReviewReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewReservationService {
    public static int REVIEW_SESSION_DURATION_IN_HOURS = 1;

    private final ReviewReservationRepository reviewReservationRepository;

    public List<ReviewReservation> findByDiscussionId(Long discussionId) {
        return reviewReservationRepository.findByDiscussionId(discussionId);
    }


    public ReviewReservation create(
            LocalDateTime startTime,
            Discussion discussion
    ) {
        Long discussionId = discussion.getId();
        List<ReviewReservation> alreadyExistReviewReservation = this.findByDiscussionId(discussionId);
        alreadyExistReviewReservation.forEach(reviewReservation -> {
            if(isDuplicatedReviewReservation(startTime, reviewReservation)) {
                throw new IllegalArgumentException("이미 예약된 시간입니다.");
            }
        });

        LiveReviewAvailableTimes liveReviewAvailableTimes = discussion.getLiveReviewAvailableTimes();

        boolean isAvailableInsert = liveReviewAvailableTimes.getTimes().stream().anyMatch(timeRange -> {
          LocalDateTime start = timeRange.getStart();
          LocalDateTime end = timeRange.getEnd();
          return start.isBefore(startTime) && end.isAfter(startTime);
        });

        if(!isAvailableInsert) {
            throw new IllegalArgumentException("예약 가능한 시간이 아닙니다.");
        }

        ReviewReservation reviewReservation = ReviewReservation.builder()
                .reviewStartDateTime(startTime)
                .discussion(discussion)
                .reviewer(discussion.getUser()) // TODO IMPLEMENT : get Context User
                .build();

//        String revieweeEmail = discussion.getUser().getEmail();
//        String reviewerEmail = reviewReservation.getReviewer().getEmail();

        //emailService.send(revieweeEmail, "예약 알림", "예약이 완료되었습니다.");
        //emailService.send(reviewerEmail, "예약 알림", "예약이 완료되었습니다.");

        return reviewReservationRepository.save(reviewReservation);
    }

    private boolean isDuplicatedReviewReservation(LocalDateTime startTime, ReviewReservation reviewReservation) {
        LocalDateTime comparerStartTime = reviewReservation.getReviewStartDateTime();
        LocalDateTime comparerEndTime = comparerStartTime.plusHours(REVIEW_SESSION_DURATION_IN_HOURS);
        boolean isEqual = comparerStartTime.isEqual(startTime);
        boolean isBetween = comparerStartTime.isBefore(startTime) && comparerEndTime.isAfter(startTime);
        boolean isStartBeforeHour = startTime.isAfter(comparerStartTime.minusHours(REVIEW_SESSION_DURATION_IN_HOURS)) && startTime.isBefore(comparerStartTime);
        return isEqual || isBetween || isStartBeforeHour;
    }
}
