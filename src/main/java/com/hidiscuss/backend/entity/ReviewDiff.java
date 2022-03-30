package com.hidiscuss.backend.entity;

import javax.persistence.*;
import java.security.spec.ECField;

@Entity
@Table(name = "ReviewDiff")
public class ReviewDiff extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "discussion_code_id", nullable = false)
    private DiscussionCode discussionCode;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_code", nullable = false)
    private String updatedCode;
}
