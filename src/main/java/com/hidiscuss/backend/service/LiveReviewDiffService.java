package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LiveReviewDiffService {

    private final LiveReviewDiffRepository liveReviewDiffRepository;

    public LiveReviewDiff findByIdAndUpdateByCodeAfter(Long diffId, String codeAfter) {

        LiveReviewDiff liveReviewDiff = liveReviewDiffRepository.findById(diffId).orElseThrow(this::NotFoundLiveDiff);
        if(liveReviewDiff == null) throw NotFoundLiveDiff();
        liveReviewDiff.setCodeAfter(codeAfter);
        liveReviewDiffRepository.save(liveReviewDiff);
        return liveReviewDiff;
    }
    private RuntimeException NotFoundLiveDiff() {
        return new IllegalArgumentException("LiveDiff not found");
    }

    public List<LiveReviewDiff> findByReviewId(Long reviewId) {
        List<LiveReviewDiff> diffList = liveReviewDiffRepository.findByReviewId(reviewId);
        if(diffList.size() == 0)
            throw NoReviewOrReviewDiff();
        return diffList;
    }

    private RuntimeException NoReviewOrReviewDiff() {
        return new IllegalArgumentException("Wrong ReivewId Or No ReviewDiff");
    }
}
