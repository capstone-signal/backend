package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PointTransaction")
public class PointTransaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "point_diff", nullable = false)
    private Long pointDiff;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Builder
    public PointTransaction(Long id, User user, Long pointDiff, String reason) {
        this.id = id;
        this.user = user;
        this.pointDiff = pointDiff;
        this.reason = reason;
    }
}
