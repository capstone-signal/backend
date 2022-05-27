package com.hidiscuss.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class ReviewDiff extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    Review review;

    @ManyToOne
    @Setter
    @JoinColumn(name = "discussion_code_id", nullable = false)
    private DiscussionCode discussionCode;

    @Setter
    @Column(columnDefinition = "mediumtext", name = "code_after", nullable = false)
    private String codeAfter;

}
