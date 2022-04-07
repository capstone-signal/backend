package com.hidiscuss.backend.entity;

public enum Status {
    ADDED(0), MODIFIED(1), REMOVED(2);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public static Status convertFromGithubStatus(String status) {
        switch (status) {
            case "added":
                return ADDED;
            case "modified":
                return MODIFIED;
            case "removed":
                return REMOVED;
            default:
                return null;
        }
    }

    public int getId() {
        return id;
    }
}
