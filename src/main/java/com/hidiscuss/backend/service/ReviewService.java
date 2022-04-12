package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateThreadRequestDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.CommentReviewDiffRepository;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import com.hidiscuss.backend.repository.DiscussionRepository;
import com.hidiscuss.backend.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DiscussionRepository discussionRepository;
    private final CommentReviewDiffRepository commentReviewDiffRepository;
    private final DiscussionCodeRepository discussionCodeRepository;

    public Review saveReview(User user, CreateCommentReviewRequestDto dto, ReviewType reviewType) {
        Discussion discussion = discussionRepository
                .findById(dto.discussionId)
                .orElseThrow(() -> new NoSuchElementException("discussionId가 없습니다."));
        Review review = Review.builder()
                .reviewer(user)
                .discussion(discussion)
                .reviewType(reviewType)
                .build();
        review = reviewRepository.save(review);
        saveCommentReviewDiff(dto, review);
        review = reviewRepository.findById(review.getId()).get();
        return review;
    }

    public void saveCommentReviewDiff(CreateCommentReviewRequestDto dto, Review review) {
        List<CommentReviewDiffDto> list = dto.diffList;
        for(CommentReviewDiffDto diff : list) {
            DiscussionCode code = discussionCodeRepository
                    .findById(diff.discussionCodeId)
                    .orElseThrow(() -> new NoSuchElementException("discussionCodeId가 없습니다."));
            CommentReviewDiff commentReviewDiff = CommentReviewDiffDto.toEntity(diff, review, code);
            commentReviewDiffRepository.save(commentReviewDiff);
        }
    }

    public ReviewThread saveThread(User user, CreateThreadRequestDto dto, Long reviewId) {
        Review review = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("reviewId가 없습니다."));
        return ReviewThread.builder()
                .user(user)
                .review(review)
                .content(dto.content)
                .build();
    }
}
