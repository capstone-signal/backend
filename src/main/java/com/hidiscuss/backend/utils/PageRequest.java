package com.hidiscuss.backend.utils;

import org.springframework.data.domain.Sort;

public final class PageRequest {

    private int page;
    private int size;
    private Sort sort;

    public PageRequest(int page, Sort sort) {
        this.page = page <= 0 ? 1 : page;
        this.size = 5;
        this.sort = sort.descending();
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(this.page - 1, size, sort);
    }
}