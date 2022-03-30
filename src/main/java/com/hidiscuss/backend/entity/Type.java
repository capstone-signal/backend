package com.hidiscuss.backend.entity;

public enum Type {
    LIVE(0), COMMENT(1);

    private int id;

    Type(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
