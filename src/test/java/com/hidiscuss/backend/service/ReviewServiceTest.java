package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.*;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.*;
import com.hidiscuss.backend.utils.PageRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
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

    private List<CreateCommentReviewDiffDto> diffList;

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

    @Test
    @DisplayName("findAllByDiscussionIdFetch_discussionId로 리뷰를 모두 가져온다")
    void findAllByDiscussionIdFetch_common() {
        PageRequest pageRequest = new PageRequest(0);
        Review review_1 = Review.builder().id(1L).build();
        Review review_2 = Review.builder().id(2L).build();
        Page<Review> entities = new PageImpl<>(List.of(review_1, review_2));
//        given(reviewRepository.findAllByDiscussionIdFetch(any(Long.class), any(pageRequest.of().getClass())))
//                .willReturn(entities);

//        Page<ReviewDto> reviewDtos = reviewService.findAllByDiscussionIdFetch(0L, pageRequest.of());

//        then(reviewDtos.getContent().size()).isEqualTo(entities.getContent().size());
    }

    private CreateCommentReviewDiffDto getCommentReviewDiffDto(Long id) {
        DiscussionCodeDto dto = new DiscussionCodeDto(id, "filename", "content");
        return new CreateCommentReviewDiffDto(dto, "codeAfter", "codeLocate", "comment");
    }
}
