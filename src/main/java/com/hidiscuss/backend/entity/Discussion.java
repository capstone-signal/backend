package com.hidiscuss.backend.entity;

import javax.persistence.*;

@Entity
@Table(name="Discussion")
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="state")
    private State state;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="question")
    private String question;

    @Column(name="live_review_required")
    private Boolean liveReviewRequired;

    @Column(name="live_review_available_times")
    private Boolean liveReviewAvailableTimes;

    @Column(name="priority")
    private Long priority;
}
