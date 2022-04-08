package com.hidiscuss.backend.entity;

public enum Status {
    ADDED(0), MODIFIED(1), REMOVED(2);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public static Status convertFromGithubStatus(String status) {
        switch (status) {
            case "modified":
                return MODIFIED;
            case "removed":
                return REMOVED;
            default: // "added" or other
                return ADDED;
        }
    }

    public int getId() {
        return id;
    }
}
