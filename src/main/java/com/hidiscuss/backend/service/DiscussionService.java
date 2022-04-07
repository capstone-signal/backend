package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.DiscussionTag;
import com.hidiscuss.backend.entity.User;
import com.hidiscuss.backend.repository.DiscussionRepository;
import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
            User user // TODO: user type
    ) {
        Discussion discussion = CreateDiscussionRequestDto.toEntity(dto);
        discussion.setUser(user);
        discussion = discussionRepository.save(discussion);

        // Processing Tag
        discussionTagService.create(discussion, dto.tagIds);
        // Processing DiscussionCode
        List<DiscussionCode> discussionCodes;
        switch(dto.discussionType) { // case가 PR or COMMIT이면 Controller에서 검증
            case "PR":
                GHPullRequest pr = githubService.getPullRequestById(Long.parseLong(dto.gitRepositoryId), Integer.parseInt(dto.gitNodeId));
                //discussionCodes = discussionCodeService.createFromPR(discussion, pr);
                break;
            case "COMMIT":
                GHCommit commit = githubService.getCommitById(Long.parseLong(dto.gitRepositoryId), dto.gitNodeId);
                discussionCodes = discussionCodeService.createFromCommit(discussion, commit, null);
                break;
            case "DIRECT":
                discussionCodes = discussionCodeService.createFromDirect(discussion, dto.codes);
                break;
        }
        return discussion;
    }
/*
    public Discussion create(Discussion discussion, List<DiscussionCode> discussionCodes) {
        Discussion savedDiscussion = discussionRepository.save(discussion);
        List<DiscussionCode> savedDiscussionCodes = discussionCodeService.create(discussionCodes, savedDiscussion);

    }

 */
}
