package com.hidiscuss.backend.utils;

import com.hidiscuss.backend.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class DbInitilization {
    public static List<Tag> getInitialTags() {
        List<String> tagNames = List.of(
                "오류해결",
                "리팩토링",
                "코드스타일",
                "react",
                "vue",
                "nextjs",
                "spring",
                "express",
                "django",
                "flask",
                "c",
                "c++",
                "python",
                "java",
                "javascript",
                "typescript",
                "kotlin",
                "tensorflow",
                "machine learning",
                "web frontend",
                "web backend",
                "android",
                "ios",
                "swift",
                "sql",
                "nosql"
        );
        return tagNames.stream().map(tag -> Tag.builder().name(tag).build()).collect(Collectors.toList());
    }
}
