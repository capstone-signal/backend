package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewThreadRepository reviewThreadRepository;
    private final DiscussionRepository discussionRepository;
    private final CommentReviewDiffService commentReviewDiffService;

    public Review saveReview(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Discussion discussion = discussionRepository
                .findByIdFetchJoin(dto.discussionId)
                .orElseThrow(() -> new NoSuchElementException("discussionId가 없습니다."));
        Review review = Review.builder()
                .reviewer(user)
                .discussion(discussion)
                .diffList(new ArrayList<>())
                .reviewType(reviewType)
                .accepted(false)
                .build();
        review = reviewRepository.save(review);
        return review;
    }

    public ReviewThread saveThread(User user, CreateThreadRequestDto dto, Long reviewId) {
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

    @Transactional(rollbackFor = Exception.class)
    public Review saveReviewAndCommentDiffList(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Review review = saveReview(user, dto, reviewType);
        review = commentReviewDiffService.saveCommentReviewDiff(dto, review);
        return review;
    }
}
