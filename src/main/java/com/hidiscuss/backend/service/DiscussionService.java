package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.DiscussionRepository;
import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestFileDetail;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final GithubService githubService;
    private final DiscussionCodeService discussionCodeService;
    private final DiscussionTagService discussionTagService;

    public Discussion create(
            CreateDiscussionRequestDto dto,
            @NotNull User user // TODO: user type
    ) {
        Discussion discussion = CreateDiscussionRequestDto.toEntity(dto, user);
        discussion = discussionRepository.save(discussion);

        // Processing Tag
        discussionTagService.create(discussion, dto.tagIds);
        // Processing DiscussionCode
        switch(dto.discussionType) { // case가 PR or COMMIT이면 Controller에서 검증
            case "PR":
                GHPullRequest pr = githubService.getPullRequestById(Long.parseLong(dto.gitRepositoryId), Integer.parseInt(dto.gitNodeId));
                List<GHPullRequestFileDetail> prFiles = githubService.getFilesByPullRequest(pr);
                discussionCodeService.createFromPR(discussion, prFiles);
                break;
            case "COMMIT":
                GHCommit commit = githubService.getCommitById(Long.parseLong(dto.gitRepositoryId), dto.gitNodeId);
                List<GHCommit.File> commitFiles = githubService.getFilesByCommit(commit);
                discussionCodeService.createFromCommit(discussion, commitFiles);
                break;
            case "DIRECT":
                discussionCodeService.createFromDirect(discussion, dto.codes);
                break;
        }
        return discussion;
    }
}
