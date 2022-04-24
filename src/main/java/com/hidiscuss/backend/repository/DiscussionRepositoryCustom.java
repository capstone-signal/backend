package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Discussion;

import java.util.List;
import java.util.Optional;

public interface DiscussionRepositoryCustom {
    Discussion findByIdFetchOrNull(Long id);
}
