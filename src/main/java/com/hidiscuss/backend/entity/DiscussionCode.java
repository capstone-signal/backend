package com.hidiscuss.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "DiscussionCode")
public class DiscussionCode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discussion_id")
    private Discussion discussion;

    @Column(name = "sha")
    private String sha;

    @Column(name = "filename")
    private String filename;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "additions")
    private Long additions;

    @Column(name = "deletions")
    private Long deletions;

    @Column(name = "changes")
    private Long changes;

    @Column(name = "content")
    private String content;

}