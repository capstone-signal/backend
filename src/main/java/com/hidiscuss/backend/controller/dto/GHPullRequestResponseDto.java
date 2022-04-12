package com.hidiscuss.backend.controller.dto;

import lombok.Getter;
import org.kohsuke.github.GHPullRequest;

import java.time.LocalDateTime;

@Getter
public class GHPullRequestResponseDto {
    private Long id;
    private String title;
    private String url;

    private GHPullRequestResponseDto(Long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }
    public static GHPullRequestResponseDto fromGHPullRequest(GHPullRequest pullRequest) {
        return new GHPullRequestResponseDto(
                (long) pullRequest.getNumber(), // ;pullRequest.getId(), memo: getId() return globally unique id
                pullRequest.getTitle(),
                pullRequest.getHtmlUrl().toString()
        );
    }
}
