package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import com.hidiscuss.backend.repository.LiveReviewDiffRepository;
import com.hidiscuss.backend.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
@AllArgsConstructor
public class LiveReviewDiffService {

    private final LiveReviewDiffRepository liveReviewDiffRepository;
    private final DiscussionCodeRepository discussionCodeRepository;
    private final ReviewRepository reviewRepository;

}
