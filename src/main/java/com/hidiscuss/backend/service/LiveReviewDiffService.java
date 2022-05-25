package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.GetReviewDiffsResponseDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        return liveReviewDiffRepository.findByReviewId(reviewId);
    }
}
