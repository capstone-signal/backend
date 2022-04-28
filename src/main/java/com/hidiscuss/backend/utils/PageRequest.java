package com.hidiscuss.backend.utils;

import org.springframework.data.domain.Sort;

public final class PageRequest {

    private int page;
    private int size;
    private Sort.Direction direction;

    public PageRequest(int page) {
        this.page = page <= 0 ? 1 : page;
        this.size = 5;
        this.direction = Sort.Direction.DESC;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(this.page - 1, size, direction, "createdAt");
    }
}