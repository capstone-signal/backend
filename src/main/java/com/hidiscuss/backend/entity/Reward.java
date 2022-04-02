package com.hidiscuss.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Reward")
public class Reward{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "reward_type", nullable = false)
    private RewardType rewardType;

    @Builder
    public Reward(Long id, User user, RewardType rewardType) {
        this.id = id;
        this.user = user;
        this.rewardType = rewardType;
    }
}
