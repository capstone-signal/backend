package com.hidiscuss.backend.service;

import com.hidiscuss.backend.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    void serviceIsDefined() {
        assertThat(tagService).isNotNull();
    }

//    @Test
//    void findAll_returnsAllTags() {
//        assertThat(tagService.findAll()).isNotNull();
//    }
//
}
