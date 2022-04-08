package com.hidiscuss.backend.utils;


import org.kohsuke.github.GitHub;

public class GithubContext {
    private static final ThreadLocal<GitHub> context = new ThreadLocal<>();

    public static GitHub getInstance() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }

    public static void setInstance(GitHub gitHub) {
        context.set(gitHub);
    }
}
