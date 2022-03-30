package com.hidiscuss.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "DiscussionCode")
public class DiscussionCode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @Column(name = "sha", nullable = false)
    private String sha;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "additions", nullable = false)
    private Long additions;

    @Column(name = "deletions", nullable = false)
    private Long deletions;

    @Column(name = "changes", nullable = false)
    private Long changes;

    @Column(name = "content", nullable = false)
    private String content;

}