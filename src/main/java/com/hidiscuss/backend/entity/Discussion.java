package com.hidiscuss.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
}
