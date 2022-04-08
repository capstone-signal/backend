package com.hidiscuss.backend.controller.dto;

import antlr.MismatchedCharException;
import lombok.Getter;
import org.kohsuke.github.GHCommit;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class GHCommitResponseDto {
    private String sha;
    private LocalDateTime date;
    private String url;
    private String message;

    private GHCommitResponseDto(String sha, LocalDateTime date, String url, String message) {
        this.sha = sha;
        this.date = date;
        this.url = url;
        this.message = message;
    }
    public static GHCommitResponseDto fromGHCommit(GHCommit ghCommit) {
        try {
            GHCommit.ShortInfo shortInfo = ghCommit.getCommitShortInfo();
            return new GHCommitResponseDto(ghCommit.getSHA1(),
                    shortInfo.getCommitDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    ghCommit.getHtmlUrl().toString(),
                    shortInfo.getMessage()
            );
        } catch (Exception e) {
            return null;
        }
    }
}
