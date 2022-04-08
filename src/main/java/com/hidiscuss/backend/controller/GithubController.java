package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.GHCommitResponseDto;
import com.hidiscuss.backend.controller.dto.GHPullRequestResponseDto;
import com.hidiscuss.backend.controller.dto.GHRepositoryResponseDto;
import com.hidiscuss.backend.utils.GithubContext;
import org.kohsuke.github.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/github")
public class GithubController {

    @GetMapping("/repos")
    public List<GHRepositoryResponseDto> getRepo(
    //        @RequestAttribute("github") GitHub github
    ) {
        GitHub github = GithubContext.getInstance();
        try {
            GHUser user = github.getUser("capstone-signal");
            Map<String, GHRepository> repos = user.getRepositories();
            return repos.values().stream().map(GHRepositoryResponseDto::fromGHRepository).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO : custom exception
        }
    }

    @GetMapping("/commits/{repoId}")
    public List<GHCommitResponseDto> getCommits(
            @PathVariable("repoId") Long repoId
    ) {
        GitHub github = GithubContext.getInstance();
        try {
            GHRepository repo = github.getRepositoryById(repoId);
            List<GHCommit> commits = repo.listCommits().toList();
            return commits.stream().map(GHCommitResponseDto::fromGHCommit).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO : custom exception
        }
    }

    @GetMapping("/prs/{repoId}")
    public List<GHPullRequestResponseDto> getPullRequests(
            @PathVariable("repoId") Long repoId
    ) {
        GitHub github = GithubContext.getInstance();
        try {
            GHRepository repo = github.getRepositoryById(repoId);
            List<GHPullRequest> pullRequests = repo.queryPullRequests().list().toList();
            return pullRequests.stream().map(GHPullRequestResponseDto::fromGHPullRequest).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e); // TODO : custom exception
        }
    }
}
