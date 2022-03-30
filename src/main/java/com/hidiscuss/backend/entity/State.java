package com.hidiscuss.backend.entity;

public enum State {
    NOT_REVIEWED(0), REVIEWING(1), COMPLETED(2);

    private int id;

    State(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
