package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "DiscussionTag",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"discussion_id", "tag_id"})
)
public class DiscussionTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Builder
    public DiscussionTag(Long id, Discussion discussion, Tag tag) {
        this.id = id;
        this.discussion = discussion;
        this.tag = tag;
    }
}
