package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateReviewReservationRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.LiveReviewAvailableTimes;
import com.hidiscuss.backend.entity.ReviewReservation;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.exception.UserAuthorityException;
import com.hidiscuss.backend.repository.ReviewReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewReservationServiceTest {
    @Mock
    private ReviewReservationRepository reviewReservationRepository;
    @InjectMocks
    private ReviewReservationService reviewReservationService;

    @Test
    void serviceIsDefined() {
        assertThat(reviewReservationService).isNotNull();
    }

    @Test
    void findByDiscussionId() {
        when(reviewReservationRepository.findByDiscussionId(1L)).thenReturn(List.of(ReviewReservation.builder().build()));

        assertThat(reviewReservationService.findByDiscussionId(1L)).isNotEmpty();
    }

//    @Test
//    void create_fail_when_review_myself() {
//        LocalDateTime givenTime = getBasisTime();
//        User user = User.builder().id(1000L).build();
//        Discussion discussion = Discussion.builder().user(User.builder().id(1000L).build()).build();
//
//        when(reviewReservationRepository.findByDiscussionId(discussion.getId())).thenReturn(List.of());
//
//        assertThrows(UserAuthorityException.class, () -> reviewReservationService.create(givenTime, discussion, user));
//    }

    @Test
    void create_fail_when_already_reserved() {
        LocalDateTime givenTime = getBasisTime();
        ReviewReservation givenReviewReservation = ReviewReservation.builder().reviewStartDateTime(givenTime).build();
        User user = User.builder().id(2000L).build();
        Discussion discussion = getDiscussion(null);
        LocalDateTime afterMinute = givenTime.plusMinutes(1);
        LocalDateTime beforeMinute = givenTime.minusMinutes(1);

        when(reviewReservationRepository.findByDiscussionId(discussion.getId())).thenReturn(List.of(givenReviewReservation));

        assertThrows(IllegalArgumentException.class, () -> reviewReservationService.create(afterMinute, discussion, user));
        assertThrows(IllegalArgumentException.class, () -> reviewReservationService.create(beforeMinute, discussion, user));
    }

    @Test
    void create_fail_not_available_times() {
        LocalDateTime givenTime = getBasisTime();
        User user = User.builder().id(2000L).build();
        LiveReviewAvailableTimes.LiveReviewAvailableTime givenAvailableTime = new LiveReviewAvailableTimes.LiveReviewAvailableTime();
        givenAvailableTime.setStart(givenTime.plusHours(1));
        givenAvailableTime.setEnd(givenTime.plusHours(2));
        LiveReviewAvailableTimes liveReviewAvailableTimes = new LiveReviewAvailableTimes(List.of(givenAvailableTime));
        Discussion discussion = getDiscussion(liveReviewAvailableTimes);

        when(reviewReservationRepository.findByDiscussionId(discussion.getId())).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, () -> reviewReservationService.create(givenTime, discussion, user));
    }

//    @Test
//    void create_success() {
//        LocalDateTime givenTime = getBasisTime();
//        User user = User.builder().id(2000L).build();
//        LiveReviewAvailableTimes.LiveReviewAvailableTime givenAvailableTime = new LiveReviewAvailableTimes.LiveReviewAvailableTime();
//        givenAvailableTime.setStart(givenTime.minusHours(1));
//        givenAvailableTime.setEnd(givenTime.plusHours(1));
//        LiveReviewAvailableTimes liveReviewAvailableTimes = new LiveReviewAvailableTimes(List.of(givenAvailableTime));
//        Discussion discussion = getDiscussion(liveReviewAvailableTimes);
//
//
//        when(reviewReservationRepository.findByDiscussionId(discussion.getId())).thenReturn(List.of());
//
//        reviewReservationService.create(givenTime, discussion, user);
//
//        // check repository save method is called
//        verify(reviewReservationRepository, times(1)).save(Mockito.any(ReviewReservation.class));
//    }

    private LocalDateTime getBasisTime() {
        return LocalDateTime.of(2022, 3, 1, 12, 0);
    }

    private Discussion getDiscussion(LiveReviewAvailableTimes liveReviewAvailableTimes) {
        return Discussion.builder()
                .id(1L)
                .liveReviewRequired(true)
                .liveReviewAvailableTimes(liveReviewAvailableTimes)
                .user(User.builder().id(1000L).build())
                .build();
    }
}
