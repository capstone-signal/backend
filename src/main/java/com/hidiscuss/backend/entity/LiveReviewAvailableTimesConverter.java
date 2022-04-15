package com.hidiscuss.backend.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LiveReviewAvailableTimesConverter implements AttributeConverter<LiveReviewAvailableTimes, String> {
    private static final Logger logger = LoggerFactory.getLogger(LiveReviewAvailableTimesConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(LiveReviewAvailableTimes attribute) {
        String stringified = null;
        if (attribute == null) {
            return null;
        }
        try {
            stringified = objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            logger.error("Convert error", e);
            throw new RuntimeException(e); // TODO : change to custom exception
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
            logger.error("Convert error", e);
            throw new RuntimeException(e); // TODO : change to custom exception
        }
        return attribute;
    }
}
