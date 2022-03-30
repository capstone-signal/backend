package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "Review",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"reviewer_id", "discussion_id", "reviwe_type"})
)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "reviwe_type", nullable = false)
    private ReviewType reviweType;

    @Builder
    public Review(Long id, User reviewer, Discussion discussion, Boolean accepted, String content, ReviewType reviweType) {
        this.id = id;
        this.reviewer = reviewer;
        this.discussion = discussion;
        this.accepted = accepted;
        this.content = content;
        this.reviweType = reviweType;
    }
}
