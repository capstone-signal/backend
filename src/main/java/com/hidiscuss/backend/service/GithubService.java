package com.hidiscuss.backend.service;

import com.hidiscuss.backend.utils.GithubContext;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class GithubService {

    private GitHub getGitHub() {
        return GithubContext.getInstance();
    }

    public Collection<GHRepository> getRepositories() {
        try {
            return getGitHub().getUser("capstone-signal").getRepositories().values(); // TODO : get user from session
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Collection<GHCommit> getCommitsByRepoId(Long repoId) {
        try {
            return getGitHub().getRepositoryById(repoId).listCommits().toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Collection<GHPullRequest> getPullRequestsByRepoId(Long repoId) {
        try {
            return getGitHub().getRepositoryById(repoId).queryPullRequests().state(GHIssueState.ALL).list().toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public GHPullRequest getPullRequestById(Long repoId, int pullRequestId) {
        try {
            return getGitHub().getRepositoryById(repoId).getPullRequest(pullRequestId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public GHCommit getCommitById(Long repoId, String sha) {
        try {
            return getGitHub().getRepositoryById(repoId).getCommit(sha);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<GHCommit.File> getFilesByCommit(GHCommit commit) {
        try {
            return commit.getFiles();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
