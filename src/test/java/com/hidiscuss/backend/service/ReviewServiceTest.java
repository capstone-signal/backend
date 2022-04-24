package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.controller.dto.DiscussionCodeDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private DiscussionRepository discussionRepository;
    @Mock
    private ReviewThreadRepository reviewThreadRepository;
    @InjectMocks
    private ReviewService reviewService;

    private User user;

    private ReviewType reviewType = ReviewType.COMMENT;

    private List<CommentReviewDiffDto> diffList;

    @BeforeEach
    void setUp() {
        user = new User();
        diffList = List.of(
                getCommentReviewDiffDto(1L),
                getCommentReviewDiffDto(2L)
        );
    }

    @Test
    @DisplayName("createReview_코멘트 리뷰와 여러 개의 diff 정보가 저장된다")
    void createReview_common() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        Discussion discussion = new Discussion();
        given(discussionRepository.findByIdFetchOrNull(any(Long.class))).willReturn(discussion);
        given(reviewRepository.save(any(Review.class))).willAnswer(i -> i.getArgument(0));

        Review review = reviewService.createReview(user, dto, reviewType);

        then(review).isNotNull();
        then(review.getReviewer()).isEqualTo(user);
        then(review.getDiscussion()).isEqualTo(discussion);
        then(review.getReviewType()).isEqualTo(reviewType);
    }

    @Test
    @DisplayName("createReview_discussion이 없을 경우 예외를 반환한다.")
    void createReview_withNoDiscussion() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        given(discussionRepository.findByIdFetchOrNull(any(Long.class))).willReturn(null);

        Throwable throwable = catchThrowable(() -> reviewService.createReview(user, dto, reviewType));

        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("createThread_thread가 저장된다")
    void createThread_common() {
        CreateThreadRequestDto dto = new CreateThreadRequestDto("comment");
        Review review = Review.builder().id(1L).build();
        given(reviewThreadRepository.save(any(ReviewThread.class))).willAnswer(i -> i.getArgument(0));

        ReviewThread reviewThread = reviewService.createThread(user, dto, review);

        then(reviewThread).isNotNull();
        then(reviewThread.getUser()).isEqualTo(user);
        then(reviewThread.getContent()).isEqualTo(dto.content);
    }

    @Test
    @DisplayName("findByIdFetchOrNull_id로 review를 찾는다")
    void findByIdFetchOrNull_common() {
        given(reviewRepository.findByIdFetchOrNull(any(Long.class))).willReturn(mock(Review.class));

        Review review = reviewService.findByIdFetchOrNull(0L);

        then(review).isNotNull();
    }

    @Test
    @DisplayName("findByIdFetchOrNull_review가 없을 경우 예외를 반환한다")
    void findByIdFetchOrNull_withNoReview() {
        given(reviewRepository.findByIdFetchOrNull(any(Long.class))).willReturn(null);

        Throwable throwable = catchThrowable(() -> reviewService.findByIdFetchOrNull(0L));

        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    private CommentReviewDiffDto getCommentReviewDiffDto(Long id) {
        DiscussionCodeDto dto = new DiscussionCodeDto(id, "filename", "content");
        return new CommentReviewDiffDto(dto, "codeAfter", "codeLocate", "comment");
    }
}
