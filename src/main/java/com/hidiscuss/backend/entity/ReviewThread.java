package com.hidiscuss.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "ReviewThread")
public class ReviewThread extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "content", nullable = false)
    private String content;
}
