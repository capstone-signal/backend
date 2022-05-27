package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "DiscussionCode")
public class DiscussionCode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Discussion discussion;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(columnDefinition = "MEDIUMTEXT", name = "content", nullable = false)
    private String content;

    @Column(name = "language", nullable = false)
    private String language;

    @Builder
    public DiscussionCode(Long id, Discussion discussion, String filename, String content, String language) {
        this.id = id;
        this.discussion = discussion;
        this.filename = filename;
        this.content = content;
        this.language = language;
    }
}