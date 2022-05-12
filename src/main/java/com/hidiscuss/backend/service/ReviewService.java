package com.hidiscuss.backend.service;


import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateLiveReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.controller.dto.ReviewDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final LiveReviewDiffService liveReviewDiffService;


    @Transactional
    public Review createCommentReview(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Discussion discussion = discussionRepository
                .findByIdFetchOrNull(dto.discussionId);
        if (discussion == null)
            throw new NoSuchElementException("Discussion not found");

        Review review = Review.builder()
                .reviewer(user)
                .discussion(discussion)
                .liveDiffList(new ArrayList<>())
                .reviewType(reviewType)
                .accepted(false)
                .build();
        review = reviewRepository.save(review);
        List<CommentReviewDiff> diffList = commentReviewDiffService.createCommentReviewDiff(review, dto.getDiffList());
        review.setCommentDiffList(diffList);
        return review;
    }

//    @Transactional
//    public Review createLiveReview(User user, long discussionId, ReviewType reviewType) {
//        Discussion discussion = discussionRepository
//                .findByIdFetchOrNull(discussionId);
//        if (discussion == null)
//            throw new NoSuchElementException("Discussion not found");
//
//        if(discussion.getState() == DiscussionState.NOT_REVIEWED)
//            discussion.setState(DiscussionState.REVIEWING);
//
//        Review review = Review.builder()
//                .reviewer(user)
//                .discussion(discussion)
//                .liveDiffList(new ArrayList<>())
//                .reviewType(reviewType)
//                .accepted(false)
//                .build();
//        review = reviewRepository.save(review);
//
//        List<LiveReviewDiff> diffList = liveReviewDiffService.createNewLiveReviewDiff(review, discussion);
//        review.setLiveDiffList(diffList);
//        return review;
//    }
//
//    @Transactional
//    public Review updateLiveReview(User user, CreateLiveReviewRequestDto dto, long reviewId) {
//        Discussion discussion = discussionRepository
//                .findByIdFetchOrNull(dto.discussionId);
//        if (discussion == null)
//            throw new NoSuchElementException("Discussion not found");
//
//        Review review = reviewRepository.findByIdFetchOrNull(reviewId);
//        System.out.println(reviewId);
//
//        if(review == null)
//            throw new NoSuchElementException("Reivew not found");
//
//        List<LiveReviewDiff> diffList = liveReviewDiffService.updateLiveReviewDiff(review, dto.getDiffList());
//        review.setLiveDiffList(diffList);
//        return review;
//    }


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

    public Page<Review> findAllByDiscussionIdFetch(Long id, PageRequest pageRequest) {
        return reviewRepository.findAllByDiscussionIdFetch(id, pageRequest);
    }
}
