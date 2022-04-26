package com.hidiscuss.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
abstract class ReviewDiff extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    Review review;

    @ManyToOne
    @JoinColumn(name = "discussion_code_id", nullable = false)
    private DiscussionCode discussionCode;

    @Column(name = "code_after", nullable = false)
    private String codeAfter;

}
