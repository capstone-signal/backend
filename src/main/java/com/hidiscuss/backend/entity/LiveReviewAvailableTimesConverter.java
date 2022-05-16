package com.hidiscuss.backend.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hidiscuss.backend.exception.LiveReviewTimeConvertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Slf4j
@Component
@RequiredArgsConstructor
public class LiveReviewAvailableTimesConverter implements AttributeConverter<LiveReviewAvailableTimes, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(LiveReviewAvailableTimes attribute) {
        String stringified = null;
        if (attribute == null) {
            return null;
        }
        try {
            stringified = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new LiveReviewTimeConvertException("Convert error");
        }
        return stringified;
    }

    @Override
    public LiveReviewAvailableTimes convertToEntityAttribute(String dbData) {
        LiveReviewAvailableTimes attribute = null;
        if (dbData == null) {
            return null;
        }
        try {
            attribute = objectMapper.readValue(dbData, LiveReviewAvailableTimes.class);
        } catch (JsonProcessingException e) {
            throw new LiveReviewTimeConvertException("Convert error");
        }
        return attribute;
    }
}
