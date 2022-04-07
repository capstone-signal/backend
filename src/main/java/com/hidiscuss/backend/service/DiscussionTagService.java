package com.hidiscuss.backend.service;

import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionTag;
import com.hidiscuss.backend.entity.Tag;
import com.hidiscuss.backend.repository.DiscussionTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DiscussionTagService {
    private final DiscussionTagRepository discussionTagRepository;
    public List<DiscussionTag> create(Discussion discussion, List<Long> tagIds) {
        List<DiscussionTag> discussionTags = new ArrayList<>();
        tagIds.forEach(tagId -> {
            DiscussionTag discussionTag = DiscussionTag.builder()
                    .discussion(discussion)
                    .tag(Tag.builder().id(tagId).build()) // no validation..
                    .build();
            discussionTags.add(discussionTag);
        });
        return discussionTagRepository.saveAll(discussionTags);
    }
}
