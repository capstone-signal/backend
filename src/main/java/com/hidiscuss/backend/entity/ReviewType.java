package com.hidiscuss.backend.entity;

public enum ReviewType {
    LIVE(0), COMMENT(1);

    private int id;

    ReviewType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
