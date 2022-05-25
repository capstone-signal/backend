package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateCommentReviewDiffDto;
import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.entity.ReviewDiff;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.CommentReviewDiffRepository;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentReviewDiffService {
    private final CommentReviewDiffRepository commentReviewDiffRepository;
    private final DiscussionCodeRepository discussionCodeRepository;

    public List<CommentReviewDiff> createCommentReviewDiff(Review review, List<CreateCommentReviewDiffDto> list) {
        List<CommentReviewDiff> diffList = new ArrayList<>();
        List<Long> idList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++)
            idList.add(list.get(i).getDiscussionCode().getId());
        List<DiscussionCode> codeList = discussionCodeRepository.findByIdList(idList);

        if (codeList.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < codeList.size(); j++) {
                    if (list.get(i).getDiscussionCode().getId().equals(codeList.get(j).getId())) {
                        CreateCommentReviewDiffDto tmpDto = list.get(i);
                        diffList.add(
                                CommentReviewDiff.builder()
                                        .review(review)
                                        .discussionCode(codeList.get(j))
                                        .codeAfter(tmpDto.getCodeAfter())
                                        .codeLocate(CreateCommentReviewDiffDto.getCodeLocateString(tmpDto.getCodeLocate()))
                                        .comment(tmpDto.getComment())
                                        .build());
                        break;
                    }
                }
            }
        }
        if (list.size() != diffList.size() || codeList.size() == 0)
            throw new EmptyDiscussionCodeException("Some discussion code is missing");
        return commentReviewDiffRepository.saveAll(diffList);
    }

    public List<CommentReviewDiff> findByReviewId(Long reviewId) {
        return commentReviewDiffRepository.findByReviewId(reviewId);
    }
}
