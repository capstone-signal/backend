package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.config.SecurityConfig;
import com.hidiscuss.backend.controller.dto.GHCommitResponseDto;
import com.hidiscuss.backend.controller.dto.GHPullRequestResponseDto;
import com.hidiscuss.backend.controller.dto.GHRepositoryResponseDto;
import com.hidiscuss.backend.service.GithubService;
import lombok.AllArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Secured(SecurityConfig.DEFAULT_ROLE)
@RestController
@RequestMapping("/github")
@AllArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping("/repo")
    public List<GHRepositoryResponseDto> getRepo(
    //        @RequestAttribute("github") GitHub github
    ) {
        Collection<GHRepository> repos = githubService.getRepositories();
        return repos.stream().map(GHRepositoryResponseDto::fromGHRepository).collect(Collectors.toList());
    }

    @GetMapping("repo/{repoId}/commit")
    public List<GHCommitResponseDto> getCommits(
            @PathVariable("repoId") Long repoId
    ) {
        Collection<GHCommit> commits = githubService.getCommitsByRepoId(repoId);
        return commits.stream().map(GHCommitResponseDto::fromGHCommit).collect(Collectors.toList());
    }

    @GetMapping("/repo/{repoId}/pr")
    public List<GHPullRequestResponseDto> getPullRequests(
            @PathVariable("repoId") Long repoId
    ) {
        Collection<GHPullRequest> pullRequests = githubService.getPullRequestsByRepoId(repoId);
        return pullRequests.stream().map(GHPullRequestResponseDto::fromGHPullRequest).collect(Collectors.toList());
    }
}
