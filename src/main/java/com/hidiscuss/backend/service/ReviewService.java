package com.hidiscuss.backend.service;


import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.exception.UserAuthorityException;
import com.hidiscuss.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final DiscussionCodeService discussionCodeService;
    private final LiveReviewDiffRepository liveReviewDiffRepository;

    @Transactional
    public Review createCommentReview(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Discussion discussion = discussionRepository
                .findByIdFetchOrNull(dto.discussionId);
        if (discussion == null)
            throw new NoSuchElementException("Discussion not found");

        if (discussion.getUser().getId().equals(user.getId()))
            throw new UserAuthorityException("Cannot review your own code");
        Review review = Review.builder()
                .reviewer(user)
                .discussion(discussion)
                .commentDiffList(new ArrayList<>())
                .reviewType(reviewType)
                .accepted(false)
                .isdone(true)
                .build();
        review = reviewRepository.save(review);
        List<CommentReviewDiff> diffList = commentReviewDiffService.createCommentReviewDiff(review, dto.getDiffList());
        review.setCommentDiffList(diffList);
        return review;
    }

    @Transactional
    public Review createLiveReivew (ReviewReservation reviewReservation)
    {
        Review review = Review.builder()
                .reviewer(reviewReservation.getReviewer())
                .discussion(reviewReservation.getDiscussion())
                .liveDiffList(new ArrayList<>())
                .reviewType(ReviewType.LIVE)
                .threadList(new ArrayList<>())
                .isdone(false)
                .accepted(false)
                .build();
        reviewRepository.save(review);

        List <DiscussionCode> discussionCodeList = discussionCodeService.findDiscussionCocde(reviewReservation.getDiscussion());

        List<LiveReviewDiff> liveReviewDiffList = new ArrayList<>();

        for (DiscussionCode code : discussionCodeList) {
            liveReviewDiffList.add(
                    LiveReviewDiff.builder()
                            .review(review)
                            .discussionCode(code)
                            .codeAfter("Not Reviewed")
                            .build()
            );
        }

        if(liveReviewDiffList.size() > 0)
            liveReviewDiffRepository.saveAll(liveReviewDiffList);
        review.setLiveDiffList(liveReviewDiffList);
        reviewRepository.save(review);

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

    public Page<Review> findAllByDiscussionIdFetch(Long id, PageRequest pageable) {
        return reviewRepository.findAllByDiscussionIdFetch(id, pageable);
    }

    public void changeCompleteStates(ReviewReservation reviewReservation) {
        if(reviewReservation.getDiscussion().getState() == DiscussionState.NOT_REVIEWED) {
            reviewReservation.getDiscussion().setState(DiscussionState.REVIEWING);
        }
        reviewReservation.getReview().setIsdone(Boolean.TRUE);
    }
}
