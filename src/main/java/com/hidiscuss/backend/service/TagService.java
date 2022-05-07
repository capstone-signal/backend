package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.DiscussionTagDto;
import com.hidiscuss.backend.controller.dto.TagResponseDto;
import com.hidiscuss.backend.entity.Tag;
import com.hidiscuss.backend.repository.TagRepository;
import com.hidiscuss.backend.utils.DbInitilization;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;

    @PostConstruct
    private void initTagService() {
        List<Tag> tags = this.findAll();
        if (!tags.isEmpty()) {
            return;
        }
        log.info("Initializing tag service");
        tags = DbInitilization.getInitialTags();
        tagRepository.saveAll(tags);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public List<DiscussionTagDto> getTags(List<String> tags) {
        return tagRepository.findAllByName(tags);
    }
}
