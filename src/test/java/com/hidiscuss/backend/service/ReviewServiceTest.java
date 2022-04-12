package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;

    @Mock private DiscussionRepository discussionRepository;

    @Mock private CommentReviewDiffRepository commentReviewDiffRepository;

    @Mock private DiscussionCodeRepository discussionCodeRepository;

    @Mock private ReviewThreadRepository reviewThreadRepository;

    @InjectMocks private ReviewService reviewService;

    private User user;

    private ReviewType reviewType = ReviewType.COMMENT;


    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("name")
                .email("email@uos.ac.kr")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .point(100L)
                .build();
    }

    @Test
    @DisplayName("saveReview_코멘트 리뷰와 여러 개의 diff 정보가 저장된다")
    void saveReview_common() {
        List<CommentReviewDiffDto> diffList = List.of(
                getCommentReviewDiffDto(),
                getCommentReviewDiffDto()
        );
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        Discussion discussion = new Discussion();
        DiscussionCode discussionCode = new DiscussionCode();
        given(discussionRepository.findById(any())).willReturn(Optional.of(discussion));
        given(discussionCodeRepository.findById(any())).willReturn(Optional.of(discussionCode));
        when(reviewRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(reviewRepository.findById(any())).thenAnswer(i -> i.getArgument(0));

        Review review = reviewService.saveReview(user, dto, reviewType);

        then(review).isNotNull();
        then(review.getReviewer()).isEqualTo(user);
        then(review.getDiscussion()).isEqualTo(discussion);
        then(review.getReviewType()).isEqualTo(reviewType);

    }
//    @Test
//    void saveReview_withNoDiscussion() {
//
//    }
//
//    void saveReview_withNoDiscussionCode() {
//
//    }
//
    @Test
    @DisplayName("saveThread_review가 없을 경우 thread가 저장되지 않고 예외를 반환한다")
    void saveThread_withNoReview() {
        CreateThreadRequestDto dto = new CreateThreadRequestDto("comment");

        Throwable throwable = catchThrowable(() -> reviewService.saveThread(user, dto, 1L));

        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("saveThread_thread가 저장된다")
    void saveThread_common() {
        CreateThreadRequestDto dto = new CreateThreadRequestDto("comment");
        Review review = Review.builder().id(1L).build();
        given(reviewRepository.findById(any())).willReturn(Optional.ofNullable(review));

        ReviewThread reviewThread = reviewService.saveThread(user, dto, 1L);

        then(reviewThread).isNotNull();
        then(reviewThread.getUser()).isEqualTo(user);
        then(reviewThread.getContent()).isEqualTo(dto.content);
    }

    private CommentReviewDiffDto getCommentReviewDiffDto() {
        return new CommentReviewDiffDto(1L, "codeAfter", "codeLocate", "comment");
    }
}
