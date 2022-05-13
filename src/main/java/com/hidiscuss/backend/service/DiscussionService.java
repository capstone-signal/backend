package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.ReviewController;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.controller.dto.GetDiscussionsDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.exception.UserAuthorityException;
import com.hidiscuss.backend.repository.DiscussionRepository;
import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestFileDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final GithubService githubService;
    private final DiscussionCodeService discussionCodeService;
    private final DiscussionTagService discussionTagService;
    private final ReviewReservationService reviewReservationService;
    private final ReviewService reviewService;
    private final StyleReviewService styleReviewService;

    public Discussion create(
            CreateDiscussionRequestDto dto,
            @NotNull User user
    ) {
        Discussion discussion = CreateDiscussionRequestDto.toEntity(dto, user);
        discussion = discussionRepository.save(discussion);

        // Processing Tag
        discussion.setTags(discussionTagService.create(discussion, dto.tagIds));
        // Processing DiscussionCode
        switch(dto.discussionType) { // case가 PR or COMMIT이면 Controller에서 검증
            case "PR":
                GHPullRequest pr = githubService.getPullRequestById(Long.parseLong(dto.gitRepositoryId), Integer.parseInt(dto.gitNodeId));
                List<GHPullRequestFileDetail> prFiles = githubService.getFilesByPullRequest(pr);
                discussionCodeService.createFromFiles(discussion, prFiles);
                break;
            case "COMMIT":
                GHCommit commit = githubService.getCommitById(Long.parseLong(dto.gitRepositoryId), dto.gitNodeId);
                List<GHCommit.File> commitFiles = githubService.getFilesByCommit(commit);
                discussionCodeService.createFromFiles(discussion, commitFiles);
                break;
            case "DIRECT":
                List<DiscussionCode> codes = discussionCodeService.createFromDirect(discussion, dto.codes);
                List<DiscussionCode> pyCodes = new ArrayList<>();
                for(DiscussionCode code : codes)
                    if (code.getLanguage().equals("Python"))
                        pyCodes.add(code);
                if (!pyCodes.isEmpty()) {
                    CreateCommentReviewRequestDto styleReviewDto
                            = new CreateCommentReviewRequestDto(
                            discussion.getId()
                            , styleReviewService.createStyleReviewDto(pyCodes));
                    User autoBot = User.builder().id(1L).build();
                    reviewService.createCommentReview(autoBot, styleReviewDto, ReviewType.COMMENT);
                }
                break;
        }
        return discussion;
    }

    public Discussion findByIdOrNull(Long discussionId) {
        return discussionRepository.findById(discussionId).orElse(null);
    }

    public Discussion findByIdFetchOrNull(Long id) {
        Discussion discussion = discussionRepository.findByIdFetchOrNull(id);
        if (discussion == null) throw new NoSuchElementException("Discussion not found");
        return discussion;
    }

    public Page<Discussion> getDiscussionsFiltered(GetDiscussionsDto dto, Pageable pageable) {
        return discussionRepository.findAllFilteredFetch(dto, pageable);
    }

    public Long delete(Discussion discussion, User user) {
        if (!discussion.getUser().getId().equals(user.getId())) {
            throw new UserAuthorityException("You can only delete discussions you have created");
        }
        Boolean notReviewed = discussion.getState().equals(DiscussionState.NOT_REVIEWED);
        Boolean noReservation = (reviewReservationService.findByDiscussionId(discussion.getId()).size() == 0);
        if (notReviewed && noReservation) {
            discussionRepository.delete(discussion);
        } else {
            throw new IllegalArgumentException("Only discussions that do not have reviews or reservations can be deleted");
        }
        return discussion.getId();
    }
}
