package com.hidiscuss.backend.utils;


import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GithubContext {
    private static final ThreadLocal<GitHub> context = new ThreadLocal<>();

    public static GitHub getInstance() {
        if (context.get() == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                throw new IllegalStateException("No authentication found in SecurityContextHolder");
            }
            String githubToken = authentication.getCredentials().toString();
            try {
                GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();
                context.set(github);
                return github;
            } catch (Exception e) {
                throw new IllegalStateException("Failed to create GitHub instance", e);
            }
        } else {
            return context.get();
        }
    }

    public static void clear() {
        context.remove();
    }
}
