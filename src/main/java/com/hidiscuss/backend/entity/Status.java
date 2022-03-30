package com.hidiscuss.backend.entity;

public enum Status {
    ADDED(0), MODIFIED(1), REMOVED(2);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
