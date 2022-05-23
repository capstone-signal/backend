package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.controller.dto.GetDiscussionsDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.exception.UserAuthorityException;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import com.hidiscuss.backend.repository.DiscussionRepository;
import com.hidiscuss.backend.repository.ReviewRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final DiscussionCodeRepository discussionCodeRepository;
    private final ReviewRepository reviewRepository;
    private final GithubService githubService;
    private final DiscussionCodeService discussionCodeService;
    private final DiscussionTagService discussionTagService;
    private final ReviewReservationService reviewReservationService;

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
                discussionCodeService.createFromDirect(discussion, dto.codes);
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
        Boolean notReviewed = discussion.getState().equals(DiscussionState.NOT_REVIEWED);
        List<ReviewReservation> reservations = reviewReservationService.findByDiscussionId(discussion.getId());
        Boolean hasNotCompletedReservation = reservations.stream().anyMatch((reservation) -> !reservation.isCompletedReservation());
        Optional<Review> styleReview = reviewRepository.findByReviewerId(9999L);

        if (!discussion.getUser().getId().equals(user.getId())) // TODO: getUser, getReviewer 등으로 변경
            throw new UserAuthorityException("You can only delete discussions you have created");
        else if (!notReviewed)
            throw new IllegalArgumentException("Only discussions that do not have reviews can be deleted");
        else if (hasNotCompletedReservation)
            throw new IllegalArgumentException("Only discussions that do not have reservations can be deleted");

        if (styleReview.isPresent())
            reviewRepository.delete(styleReview.get());
        discussionCodeRepository.deleteByDiscussion(discussion);
        discussionRepository.delete(discussion);

        return discussion.getId();
    }

    public Long complete(Discussion discussion, User user) {
        List<ReviewReservation> reservations = reviewReservationService.findByDiscussionId(discussion.getId());
        // 하나라도 지금 이후라면
        Boolean hasNotCompletedReservation = reservations.stream().anyMatch((reservation) -> !reservation.isCompletedReservation());

        if (!discussion.getUser().getId().equals(user.getId()))
            throw new UserAuthorityException("You can only complete discussions you have created");
        else if (hasNotCompletedReservation)
            throw new IllegalArgumentException("Only discussions that do not have reservations can be completed");

        discussion.complete();
        discussionRepository.save(discussion);
        return discussion.getId();
    }
}
