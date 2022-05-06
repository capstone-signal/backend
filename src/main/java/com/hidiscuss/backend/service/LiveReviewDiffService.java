package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateLiveReviewDiffDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.LiveReviewDiff;
import com.hidiscuss.backend.entity.Review;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LiveReviewDiffService {
    private final LiveReviewDiffRepository liveReviewDiffRepository;
    private final DiscussionCodeRepository discussionCodeRepository;

    public List<LiveReviewDiff> updateLiveReviewDiff(Review review, List<CreateLiveReviewDiffDto> list) {
        List<LiveReviewDiff> diffList = new ArrayList<>();
        List<Long> idList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            idList.add(list.get(i).getDiscussionCode().getId());
        }
        List<DiscussionCode> codeList = discussionCodeRepository.findByIdListFetch(idList);

        if(codeList.size() > 0) {
            for(int i = 0; i < list.size(); i++){
                for (int j = 0; j <codeList.size(); j++){
                    if(list.get(i).getDiscussionCode().getId().equals(codeList.get(j).getId())) {
                        CreateLiveReviewDiffDto tmpDto = list.get(i);
                        diffList.add(
                                LiveReviewDiff.builder()
                                        .review(review)
                                        .discussionCode(codeList.get(j))
                                        .codeAfter(tmpDto.getCodeAfter())
                                        .build());
                        break;
                    }
                }
            }
        }
        if(list.size() != diffList.size() || codeList.size() == 0)
            throw new EmptyDiscussionCodeException("Some Discussion code is missing");
        return liveReviewDiffRepository.saveAll(diffList);

    }

    public List<LiveReviewDiff> createNewLiveReviewDiff(Review review, Discussion discussion) {
        List<LiveReviewDiff> diffList = new ArrayList<>();

        List<DiscussionCode> codeList = discussionCodeRepository.findByDiscussion(discussion);

        if(codeList.size() > 0) {
            for (DiscussionCode discussionCode : codeList) {
                diffList.add(
                        LiveReviewDiff.builder()
                                .review(review)
                                .discussionCode(discussionCode)
                                .codeAfter("not Changed")
                                .build());
            }
        }
        return liveReviewDiffRepository.saveAll(diffList);
    }
}
