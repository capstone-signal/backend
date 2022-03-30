package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

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
    @JoinColumn(name = "reviwe_id", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @Column(name = "review_date", nullable = false)
    private Timestamp reviewDate;

    @Column(name = "reviwer_participated", nullable = false)
    private Boolean reviwerParticipated;

    @Column(name = "reviwee_participated", nullable = false)
    private Boolean reviweeParticipated;

    @Column(name = "progressed", nullable = false)
    private Boolean progressed;

    @Builder
    public ReviewReservation(Long id, User reviewer, Review review, Discussion discussion, Timestamp reviewDate, Boolean reviwerParticipated, Boolean reviweeParticipated, Boolean progressed) {
        this.id = id;
        this.reviewer = reviewer;
        this.review = review;
        this.discussion = discussion;
        this.reviewDate = reviewDate;
        this.reviwerParticipated = reviwerParticipated;
        this.reviweeParticipated = reviweeParticipated;
        this.progressed = progressed;
    }
}
