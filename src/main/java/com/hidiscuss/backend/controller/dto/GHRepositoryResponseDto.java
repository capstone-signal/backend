package com.hidiscuss.backend.controller.dto;

import lombok.Getter;
import org.kohsuke.github.GHRepository;

@Getter
public class GHRepositoryResponseDto {
    private String id;
    private String fullName;
    private String url;

    private GHRepositoryResponseDto(long id, String fullName, String url) {
        this.id = String.valueOf(id);
        this.fullName = fullName;
        this.url = url;
    }

    public static GHRepositoryResponseDto fromGHRepository(GHRepository ghRepository) {
        return new GHRepositoryResponseDto(ghRepository.getId(), ghRepository.getFullName(), ghRepository.getHtmlUrl().toString());
    }
}
