package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.*;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewThreadRepository reviewThreadRepository;
    private final DiscussionRepository discussionRepository;
    private final CommentReviewDiffRepository commentReviewDiffRepository;
    private final LiveReviewDiffRepository liveReviewDiffRepository;
    private final DiscussionCodeRepository discussionCodeRepository;

    public Review saveReview(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Discussion discussion = discussionRepository
                .findById(dto.discussionId)
                .orElseThrow(() -> new NoSuchElementException("discussionId가 없습니다."));
        Review review = Review.builder()
                .reviewer(user)
                .discussion(discussion)
                .reviewType(reviewType)
                .accepted(false)
                .reviewer(user)
                .build();
        review = reviewRepository.save(review);
        return review;
    }

    public Review saveLiveReview(User user, CreateLiveReviewRequestDto dto, ReviewType reviewType) {
        Discussion discussion = discussionRepository
                .findById(dto.discussionId)
                .orElseThrow(() -> new NoSuchElementException("discussionId가 없습니다."));
        Review review = Review.builder()
                .reviewer(user)
                .discussion(discussion)
                .reviewType(reviewType)
                .accepted(false)
                .reviewer(user)
                .build();
        review = reviewRepository.save(review);
        return review;
    }

    public Review saveCommentReviewDiff(CreateCommentReviewRequestDto dto, Review review) {
        List<CommentReviewDiffDto> list = dto.diffList;
        for(CommentReviewDiffDto item : list) {
            DiscussionCode code = discussionCodeRepository
                    .findById(item.getDiscussionCode().getId())
                    .orElseThrow(() -> new NoSuchElementException("discussionCodeId가 없습니다."));
            CommentReviewDiff commentReviewDiff = CommentReviewDiffDto.toEntity(item, review, code);
            commentReviewDiffRepository.save(commentReviewDiff);
        }
        List<CommentReviewDiff> entityList = commentReviewDiffRepository.findByReviewId(review.getId());
        review.setDiffList(entityList);
        review = reviewRepository.save(review);
        return review;
    }

    public Review saveLiveReviewDiff(CreateLiveReviewRequestDto dto, Review review) {
        LiveReviewDiffDto liveReviewDiffDto = dto.liveReviewDiffDto;

        DiscussionCode code = discussionCodeRepository
                .findById(liveReviewDiffDto.getDiscussionCode().getId())
                .orElseThrow(() -> new NoSuchElementException("discussionCodeId가 없습니다."));

        LiveReviewDiff liveReviewDiff = LiveReviewDiffDto.toEntity(liveReviewDiffDto,review, code);
        liveReviewDiffRepository.save(liveReviewDiff);

        review = reviewRepository.save(review);
        return review;
    }

    public ReviewThread saveThread(User user, CreateThreadRequestDto dto, Long reviewId) {
        Review review = reviewRepository
                .findById(reviewId)
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
