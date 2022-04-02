package com.hidiscuss.backend.entity;

public enum RewardType {
    BADGE(0), PRIORITY(1);

    private int id;

    RewardType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
