package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Discussion")
public class Discussion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "live_review_required", nullable = false)
    private Boolean liveReviewRequired;

    @Column(name = "live_review_available_times")
    private Boolean liveReviewAvailableTimes;

    @Column(name = "priority", nullable = false)
    private Long priority;

    @Builder
    public Discussion(Long id, State state, User user, String question, Boolean liveReviewRequired, Boolean liveReviewAvailableTimes, Long priority) {
        this.id = id;
        this.state = state;
        this.user = user;
        this.question = question;
        this.liveReviewRequired = liveReviewRequired;
        this.liveReviewAvailableTimes = liveReviewAvailableTimes;
        this.priority = priority;
    }
}
