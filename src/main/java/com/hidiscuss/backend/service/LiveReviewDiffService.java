package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LiveReviewDiffService {

    private final LiveReviewDiffRepository liveReviewDiffRepository;

    public LiveReviewDiff updateDiff(LiveReviewDiff liveReviewDiff, String codeAfter) {
        liveReviewDiff.setCodeAfter(codeAfter);
        liveReviewDiffRepository.save(liveReviewDiff);
        return liveReviewDiff;
    }
}
