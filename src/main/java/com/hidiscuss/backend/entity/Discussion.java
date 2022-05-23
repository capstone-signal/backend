package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Discussion")
public class Discussion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('NOT_REVIEWED', 'REVIEWING', 'COMPLETED') default 'NOT_REVIEWED'", name = "state", nullable = false)
    private DiscussionState state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "varchar(10000) default ''", name = "question", nullable = false)
    private String question;

    @Column(columnDefinition = "boolean default false", name = "live_review_required", nullable = false)
    private Boolean liveReviewRequired;

    @Column(columnDefinition = "json default null", name = "live_review_available_times")
    @Convert(converter = LiveReviewAvailableTimesConverter.class)
    private LiveReviewAvailableTimes liveReviewAvailableTimes;

    @Column(columnDefinition = "bigint default 0", name = "priority", nullable = false)
    private Long priority;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscussionTag> tags = new ArrayList<>();

    @Builder
    public Discussion(Long id, User user, String question, String title, Boolean liveReviewRequired, LiveReviewAvailableTimes liveReviewAvailableTimes, Long priority) {
        this.id = id;
        this.state = DiscussionState.NOT_REVIEWED;
        this.user = user;
        this.title = title;
        this.question = question;
        this.liveReviewRequired = liveReviewRequired;
        this.liveReviewAvailableTimes = liveReviewAvailableTimes;
        this.priority = priority;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTags(List<DiscussionTag> tags) { this.tags = tags; }

    public void complete() { this.state = DiscussionState.COMPLETED; }

    public void reviewing() { this.state = DiscussionState.REVIEWING; }
}
