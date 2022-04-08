package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionCodeRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscussionCodeServiceTest {
    @Mock
    private DiscussionCodeRepository discussionCodeRepository;

    @InjectMocks
    private DiscussionCodeService discussionCodeService;

    private Discussion discussion = null;
    @Test
    public void isDefined() {
        assertThat(discussionCodeService).isNotNull();
    }

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(discussionCodeRepository.saveAll(Mockito.any())).thenAnswer(i -> i.getArgument(0));
        discussion = new Discussion();
    }

    @Test
    public void createFromDirectTest() {
           List<CreateDiscussionCodeRequestDto> createDiscussionCodeRequestDtos = List.of(
                   getCreateDiscussionCodeRequestDto(),
                   getCreateDiscussionCodeRequestDto()
           );

           List<DiscussionCode> discussionCodes = discussionCodeService.createFromDirect(discussion, createDiscussionCodeRequestDtos);

           assertThat(discussionCodes).isNotNull();
           assertThat(discussionCodes.size()).isEqualTo(createDiscussionCodeRequestDtos.size());
    }

    @Test
    public void createFromCommit_withNoPatch() {
        List<GHCommit.File> files = new ArrayList<>();
        GHCommit.File file = Mockito.mock(GHCommit.File.class);
        when(file.getPatch()).thenReturn(null);
        files.add(file);

        Exception ex = assertThrows(EmptyDiscussionCodeException.class, () -> discussionCodeService.createFromCommit(discussion, files));

        assertThat(ex).isNotNull();
    }

    @Test
    public void createFromCommit() {
        List<GHCommit.File> files = new ArrayList<>();
        GHCommit.File file = Mockito.mock(GHCommit.File.class);
        when(file.getFileName()).thenReturn("test.java");
        when(file.getPatch()).thenReturn("test");
        when(file.getStatus()).thenReturn("added");
        files.add(file);

        List<DiscussionCode> discussionCodes = discussionCodeService.createFromCommit(discussion, files);

        assertThat(discussionCodes).isNotNull();
        assertThat(discussionCodes.size()).isEqualTo(files.size());
    }

    @Test
    public void createFromPR_withNoPatch() {
        GHPullRequest pr = Mockito.mock(GHPullRequest.class);
        GHPullRequestFileDetail file = Mockito.mock(GHPullRequestFileDetail.class);
        List<GHPullRequestFileDetail> files = List.of(file);


        Exception ex = assertThrows(EmptyDiscussionCodeException.class, () -> discussionCodeService.createFromPR(discussion, files));

        assertThat(ex).isNotNull();
    }

    @Test
    public void createFromPullRequest() {
        GHPullRequest pr = Mockito.mock(GHPullRequest.class);
        GHPullRequestFileDetail file = Mockito.mock(GHPullRequestFileDetail.class);
        List<GHPullRequestFileDetail> files = List.of(file);
        when(file.getFilename()).thenReturn("test.java");
        when(file.getPatch()).thenReturn("test");
        when(file.getStatus()).thenReturn("added");


        List<DiscussionCode> discussionCodes = discussionCodeService.createFromPR(discussion, files);

        assertThat(discussionCodes).isNotNull();
        assertThat(discussionCodes.size()).isEqualTo(files.size());
    }

    private CreateDiscussionCodeRequestDto getCreateDiscussionCodeRequestDto() {
        CreateDiscussionCodeRequestDto createDiscussionCodeRequestDto = new CreateDiscussionCodeRequestDto();
        createDiscussionCodeRequestDto.filename = "filename";
        createDiscussionCodeRequestDto.content = "code";
        return createDiscussionCodeRequestDto;
    }
}
