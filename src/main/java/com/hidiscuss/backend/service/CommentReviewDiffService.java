package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.entity.CommentReviewDiff;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.repository.CommentReviewDiffRepository;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentReviewDiffService {
    private final CommentReviewDiffRepository commentReviewDiffRepository;
    private final DiscussionCodeRepository discussionCodeRepository;

    public List<CommentReviewDiff> createCommentReviewDiff(Review review, List<CommentReviewDiffDto> list) {
        List<CommentReviewDiff> diffList = null;
        List<Long> idList = null;
        for(int i = 0; i < list.size(); i++)
            idList.add(list.get(i).getDiscussionCode().getId());
        List<DiscussionCode> codeList = discussionCodeRepository.findByIdListFetchJoin(idList);
        for(int i = 0; i < list.size(); i++)
            diffList.add(CommentReviewDiffDto.toEntity(list.get(i), review, codeList.get(i)));
        commentReviewDiffRepository.saveAll(diffList);
        return diffList;
    }
}
