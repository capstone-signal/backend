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
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
    @DisplayName("saveReview_코멘트 리뷰와 여러 개의 diff 정보가 저장된다")
    void saveReview_common() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        Discussion discussion = new Discussion();
        given(discussionRepository.findById(any(Long.class))).willReturn(Optional.of(discussion));
        given(reviewRepository.save(any(Review.class))).willAnswer(i -> i.getArgument(0));

        Review review = reviewService.saveReview(user, dto, reviewType);

        then(review).isNotNull();
        then(review.getReviewer()).isEqualTo(user);
        then(review.getDiscussion()).isEqualTo(discussion);
        then(review.getReviewType()).isEqualTo(reviewType);
    }

    @Test
    @DisplayName("saveReview_discussion이 없을 경우 예외를 반환한다.")
    void saveReview_withNoDiscussion() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        given(discussionRepository.findById(any(Long.class))).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> reviewService.saveReview(user, dto, reviewType));

        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("saveCommentReviewDiff_commentReviewDiff가 저장된다.")
    void saveCommentReviewDiff_common() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        DiscussionCode discussionCode = new DiscussionCode();
        Review review = new Review();
        given(discussionCodeRepository.findById(any(Long.class))).willReturn(Optional.of(discussionCode));
        given(commentReviewDiffRepository.save(any(CommentReviewDiff.class))).willAnswer(i -> i.getArgument(0));
        given(reviewRepository.findById(any(Long.class))).willAnswer(i -> i.getArgument(0));

        review = reviewService.saveCommentReviewDiff(dto, review);

        then(review.getDiffList()).isEqualTo(diffList);
        then(review.getDiffList().size()).isEqualTo(diffList.size());
    }

    @Test
    @DisplayName("saveCommentReviewDiff_discussionCode가 없을 경우 예외를 반환한다.")
    void saveCommentReviewDiff_withNoDiscussionCode() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        DiscussionCode discussionCode = new DiscussionCode();
        Review review = new Review();
        given(discussionCodeRepository.findById(any(Long.class))).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> reviewService.saveCommentReviewDiff(dto, review));

        then(throwable).isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("saveThread_thread가 저장된다")
    void saveThread_common() {
        CreateThreadRequestDto dto = new CreateThreadRequestDto("comment");
        Review review = Review.builder().id(1L).build();
        given(reviewRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(review));

        ReviewThread reviewThread = reviewService.saveThread(user, dto, 1L);

        then(reviewThread).isNotNull();
        then(reviewThread.getUser()).isEqualTo(user);
        then(reviewThread.getContent()).isEqualTo(dto.content);
    }

    @Test
    @DisplayName("saveThread_review가 없을 경우 예외를 반환한다")
    void saveThread_withNoReview() {
        CreateThreadRequestDto dto = new CreateThreadRequestDto("comment");

        Throwable throwable = catchThrowable(() -> reviewService.saveThread(user, dto, 1L));

        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    private CommentReviewDiffDto getCommentReviewDiffDto(Long id) {
        DiscussionCodeDto dto = new DiscussionCodeDto(id, "filename", "content");
        return new CommentReviewDiffDto(dto, "codeAfter", "codeLocate", "comment");
    }
}
