package com.hidiscuss.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class LiveReviewAvailableTimes {
    private List<LiveReviewAvailableTime> times;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveReviewAvailableTimes that = (LiveReviewAvailableTimes) o;
        return times.equals(that.times);
    }

    @Override
    public int hashCode() {
        return Objects.hash(times);
    }

    public LiveReviewAvailableTimes(List<LiveReviewAvailableTime> times) {
        this.times = times;
    }

    @Getter
    @Setter
    public static class LiveReviewAvailableTime {
        private ZonedDateTime start;
        private ZonedDateTime end;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LiveReviewAvailableTime that = (LiveReviewAvailableTime) o;
            return Objects.equals(start, that.start) && Objects.equals(end, that.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }
}
