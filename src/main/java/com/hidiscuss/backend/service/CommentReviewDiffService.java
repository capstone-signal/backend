package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.repository.CommentReviewDiffRepository;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import com.hidiscuss.backend.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CommentReviewDiffService {
    private final ReviewRepository reviewRepository;
    private final CommentReviewDiffRepository commentReviewDiffRepository;
    private final DiscussionCodeRepository discussionCodeRepository;

    public Review saveCommentReviewDiff(CreateCommentReviewRequestDto dto, Review review) {
        List<CommentReviewDiffDto> list = dto.diffList;
        for(CommentReviewDiffDto item : list) {
            DiscussionCode code = discussionCodeRepository
                    .findByIdFetchJoin(item.getDiscussionCode().getId())
                    .orElseThrow(() -> new NoSuchElementException("discussionCodeId가 없습니다."));
            CommentReviewDiff commentReviewDiff = CommentReviewDiffDto.toEntity(item, review, code);
            commentReviewDiff.setReview(review);
            commentReviewDiffRepository.save(commentReviewDiff);
        }
        return review;
    }
}
