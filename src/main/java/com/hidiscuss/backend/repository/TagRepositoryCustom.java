package com.hidiscuss.backend.repository;

import com.hidiscuss.backend.controller.dto.DiscussionTagDto;
import com.hidiscuss.backend.controller.dto.TagResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TagRepositoryCustom {
    List<DiscussionTagDto> findAllByName(List<String> tags);
}
