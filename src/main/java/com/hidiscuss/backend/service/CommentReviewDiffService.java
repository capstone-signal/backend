package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateCommentReviewDiffDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.CommentReviewDiffRepository;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<CommentReviewDiff> tmp = new ArrayList<>();
        List<CommentReviewDiff> diffList = commentReviewDiffRepository.findByReviewId(reviewId);
        if (Objects.equals(diffList, tmp))
            throw NoReviewOrReviewDiff();
        return diffList;
    }

    private RuntimeException NoReviewOrReviewDiff() {
        return new IllegalArgumentException("Wrong ReivewId Or No ReviewDiff");
    }
}
