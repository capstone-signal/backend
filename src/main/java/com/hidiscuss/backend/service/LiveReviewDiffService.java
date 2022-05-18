package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LiveReviewDiffService {

    private final LiveReviewDiffRepository liveReviewDiffRepository;

    public LiveReviewDiff findByIdAndUpdateByCodeAfter(Long diffId, String codeAfter, Long userId) {

        LiveReviewDiff liveReviewDiff = liveReviewDiffRepository.findById(diffId).orElse(null);
        if(liveReviewDiff == null) throw NotFoundLiveDiff();
        liveReviewDiff.setCodeAfter(codeAfter);
        liveReviewDiffRepository.save(liveReviewDiff);
        return liveReviewDiff;
    }
    private RuntimeException NotFoundLiveDiff() {
        return new IllegalArgumentException("LiveDiff not found");
    }
}
