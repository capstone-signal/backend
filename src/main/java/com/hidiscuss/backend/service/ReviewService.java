package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewThreadRepository reviewThreadRepository;
    private final DiscussionRepository discussionRepository;
    private final CommentReviewDiffService commentReviewDiffService;

    @Transactional
    public Review createCommentReview(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Review review = createReview(user, dto, reviewType);
        List<CommentReviewDiff> diffList = commentReviewDiffService.createCommentReviewDiff(review, dto.getDiffList());
        review.setCommentDiffList(diffList);
        return review;
    }

    //TODO: commentDiffList로만 만들어지는 것 liveDiffList로도 적용되도록 일반화
    public Review createReview(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Discussion discussion = discussionRepository
                .findByIdFetchJoin(dto.discussionId)
                .orElseThrow(() -> new NoSuchElementException("discussionId가 없습니다."));
        Review review = Review.builder()
                .reviewer(user)
                .discussion(discussion)
                .commentDiffList(new ArrayList<>())
                .reviewType(reviewType)
                .accepted(false)
                .build();
        review = reviewRepository.save(review);
        return review;
    }

    public ReviewThread createThread(User user, CreateThreadRequestDto dto, Long reviewId) {
        Review review = reviewRepository
                .findByIdFetchJoin(reviewId)
                .orElseThrow(() -> new NoSuchElementException("reviewId가 없습니다."));
        ReviewThread reviewThread = ReviewThread.builder()
                .user(user)
                .review(review)
                .content(dto.content)
                .build();
        reviewThreadRepository.save(reviewThread);
        return reviewThread;
    }
}
