package com.hidiscuss.backend.entity;

import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, DiscussionState> {
    @Override
    public DiscussionState convert(String source) {
        return DiscussionState.valueOf(source.toUpperCase());
    }
}