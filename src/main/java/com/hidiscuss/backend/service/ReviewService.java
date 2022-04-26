package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.*;

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
                .findByIdFetchOrNull(dto.discussionId);
        if (discussion == null)
            throw new NoSuchElementException("Discussion not found");
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


    public ReviewThread createThread(User user, CreateThreadRequestDto dto, Review review) {
        ReviewThread reviewThread = ReviewThread.builder()
                .user(user)
                .review(review)
                .content(dto.content)
                .build();
        reviewThreadRepository.save(reviewThread);
        return reviewThread;
    }

    public Review findByIdFetchOrNull(Long id) {
        Review review = reviewRepository.findByIdFetchOrNull(id);
        if (review == null) throw new NoSuchElementException("Review not found");
        return review;
    }
}
