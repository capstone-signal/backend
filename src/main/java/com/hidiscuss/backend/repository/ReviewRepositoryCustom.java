package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.Review;

public interface ReviewRepositoryCustom {

    Review findByIdFetchOrNull(Long id);
}
