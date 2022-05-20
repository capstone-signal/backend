package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ReviewReservation")
public class ReviewReservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @ManyToOne
    @Setter
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @Column(name = "review_start_date", nullable = false)
    private LocalDateTime reviewStartDateTime;

    @Setter
    @Column(columnDefinition ="boolean default false", name = "reviewer_participated", nullable = false)
    private Boolean reviewerParticipated;

    @Setter
    @Column(columnDefinition ="boolean default false", name = "reviewee_participated", nullable = false)
    private Boolean revieweeParticipated;

    @Column(columnDefinition ="boolean default false", name = "isdone", nullable = false)
    private Boolean isdone;

    @Builder
    public ReviewReservation(Long id, User reviewer, Review review, Discussion discussion, LocalDateTime reviewStartDateTime) {
        this.id = id;
        this.reviewer = reviewer;
        this.review = review;
        this.discussion = discussion;
        this.reviewStartDateTime = reviewStartDateTime;
        this.reviewerParticipated = false;
        this.revieweeParticipated = false;
        this.isdone = false;
    }

    public Boolean isCompletedReservation() {
        // TODO: isDone이 true인 것과 or연산
        return reviewStartDateTime.plusHours(1).isBefore(LocalDateTime.now());
    }
}
