package com.hidiscuss.backend.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LiveReviewAvailableTimes {
    private List<LiveReviewAvailableTime> times;

    @Getter
    private static class LiveReviewAvailableTime {
        private LocalDateTime start;
        private LocalDateTime end;
    }
}
