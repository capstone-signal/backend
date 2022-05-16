package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CompleteLiveReviewRequestDto;
import com.hidiscuss.backend.controller.dto.SendEmailDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import com.hidiscuss.backend.repository.ReviewRepository;
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
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewReservationService {
    public static int REVIEW_SESSION_DURATION_IN_HOURS = 1;

    private final ReviewReservationRepository reviewReservationRepository;
    private final LiveReviewDiffRepository liveReviewDiffRepository;
    private final EmailService emailService;
    private final ReviewRepository reviewRepository;
    private final DiscussionCodeRepository discussionCodeRepository;

    public List<ReviewReservation> findByDiscussionId(Long discussionId) {
        return reviewReservationRepository.findByDiscussionId(discussionId);
    }

    public List<ReviewReservation> findByDiscussionIdAndUserId(Long userId) {
        return reviewReservationRepository.findByUserId(userId);
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

        String revieweeEmail = discussion.getUser().getEmail();
        String reviewerEmail = reviewReservation.getReviewer().getEmail();

        emailService.send(new SendEmailDto(revieweeEmail, getSubject(), getContent(startTime)));
        emailService.send(new SendEmailDto(reviewerEmail, getSubject(), getContent(startTime)));

        return reviewReservationRepository.save(reviewReservation);
    }

    private String getSubject() {
        return "[Hidiscuss] 라이브 리뷰 예약 완료";
    }

    private String getContent(LocalDateTime reviewStartTime) {
        StringBuilder sb = new StringBuilder();
        // utc to asia/seoul
        ZonedDateTime zonedDateTime = ZonedDateTime.of(reviewStartTime, ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        String reviewStartTimeStr = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분"));
        sb.append("<h1>예약이 완료되었습니다.</h1>");
        sb.append("<p>예약이 완료되었습니다.</p>");
        sb.append("<p>예약 시간 : ").append(reviewStartTimeStr).append("</p>");
        return sb.toString();
    }

    private boolean isDuplicatedReviewReservation(LocalDateTime startTime, ReviewReservation reviewReservation) {
        LocalDateTime comparerStartTime = reviewReservation.getReviewStartDateTime();
        LocalDateTime comparerEndTime = comparerStartTime.plusHours(REVIEW_SESSION_DURATION_IN_HOURS);
        boolean isEqual = comparerStartTime.isEqual(startTime);
        boolean isBetween = comparerStartTime.isBefore(startTime) && comparerEndTime.isAfter(startTime);
        boolean isStartBeforeHour = startTime.isAfter(comparerStartTime.minusHours(REVIEW_SESSION_DURATION_IN_HOURS)) && startTime.isBefore(comparerStartTime);
        return isEqual || isBetween || isStartBeforeHour;
    }



    public ReviewReservation findByIdOrNull(Long reservationId) {
            return reviewReservationRepository.findById(reservationId).orElse(null);
    }

    public void saveAll(CompleteLiveReviewRequestDto completeLiveReviewRequestDto) {
        Set<String> ids = completeLiveReviewRequestDto.changeCode.keySet();

        for(String id:ids){
            Long parseId = Long.parseLong(id);
            LiveReviewDiff liveReviewDiff = liveReviewDiffRepository.findById(parseId).orElse(null);
            if(liveReviewDiff != null) {
                liveReviewDiff.setCodeAfter(completeLiveReviewRequestDto.changeCode.get(id));
                liveReviewDiffRepository.save(liveReviewDiff);
            }
        }
    }


    public ReviewReservation createNewLiveReviewAndDiffs(List<Long> discussionCodeIds, ReviewReservation reviewReservation) {

        Review review = Review.builder()
                .reviewer(reviewReservation.getReviewer())
                .discussion(reviewReservation.getDiscussion())
                .reviewType(ReviewType.LIVE)
                .accepted(false)
                .build();
        reviewRepository.save(review);

        List <LiveReviewDiff> liveReviewDiffList =  new ArrayList<>();

        for (Long code : discussionCodeIds) {
            discussionCodeRepository.findById(code).ifPresent(discussionCode -> liveReviewDiffList.add(
                    LiveReviewDiff.builder()
                            .review(review)
                            .discussionCode(discussionCode)
                            .codeAfter("null")
                            .build()
            ));
        }

        if(liveReviewDiffList.size() > 0)
            liveReviewDiffRepository.saveAll(liveReviewDiffList);

        review.setLiveDiffList(liveReviewDiffList);
        reviewRepository.save(review);
        reviewReservation.setReview(review);
        reviewReservationRepository.save(reviewReservation);
        return reviewReservation;
    }

    public ReviewReservation checkUser(ReviewReservation reviewReservation,Long userId){
        if(!Objects.equals(reviewReservation.getReviewer().getId(), userId) && !Objects.equals(reviewReservation.getDiscussion().getUser().getId(), userId)){
            throw  NoReviewerOrReviewee();
        }
        if(reviewReservation.getReviewer().getId().equals(userId)){
            reviewReservation.setReviewerParticipated(true);
        } else{
            reviewReservation.setRevieweeParticipated(true);
        }
        return reviewReservation;
    }
    private RuntimeException NoReviewerOrReviewee() {
        return new IllegalArgumentException("You are not Reviewee Or Reviewer");
    }
}
