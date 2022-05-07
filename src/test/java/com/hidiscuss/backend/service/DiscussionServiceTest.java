package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionCodeRequestDto;
import com.hidiscuss.backend.controller.dto.CreateDiscussionRequestDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.DiscussionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscussionServiceTest {

    @Mock
    private DiscussionRepository discussionRepository;

    @Mock
    private DiscussionCodeService discussionCodeService;

    @Mock
    private DiscussionTagService discussionTagService;

    @InjectMocks
    private DiscussionService discussionService;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(discussionRepository.save(Mockito.any(Discussion.class))).thenAnswer(i -> i.getArgument(0));
    }
    @Test
    void serviceIsDefined() {
        assertThat(discussionService).isNotNull();
    }

    @Test
    void createDiscussion_common() {
        CreateDiscussionRequestDto dto = getCreateDiscussionRequestDto();
        User user = new User();

        Discussion discussion = discussionService.create(dto, user);

        assertThat(discussion.getState()).isEqualTo(DiscussionState.NOT_REVIEWED);
        assertThat(discussion.getUser()).isEqualTo(user);
        assertThat(discussion.getQuestion()).isNotNull();
    }

    @Test
    void createDiscussion_onlyCommentReview() {
        CreateDiscussionRequestDto dto = getCreateDiscussionRequestDto();
        dto.liveReviewRequired = false;
        dto.liveReviewAvailableTimes = null;
        User user = new User();

        Discussion discussion = discussionService.create(dto, user);

        assertThat(discussion.getLiveReviewRequired()).isFalse();
        assertThat(discussion).isNotNull();
    }

    @Test
    void createDiscussion_withLiveReview() {
        CreateDiscussionRequestDto dto = getCreateDiscussionRequestDto();
        dto.liveReviewRequired = true;
        dto.liveReviewAvailableTimes = new LiveReviewAvailableTimes(List.of());
        User user = new User();

        Discussion discussion = discussionService.create(dto, user);

        assertThat(discussion.getLiveReviewRequired()).isTrue();
        assertThat(discussion.getLiveReviewAvailableTimes()).isNotNull();
        assertThat(discussion).isNotNull();
    }

    @Test
    void findById_Nullable() {
        when(discussionRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Discussion discussion = discussionService.findByIdOrNull(1L);

        assertThat(discussion).isNull();
    }

    @Test
    @DisplayName("findByIdFetchOrNull_id로 discussion를 찾는다")
    void findByIdFetchOrNull_common() {
        given(discussionRepository.findByIdFetchOrNull(any(Long.class))).willReturn(mock(Discussion.class));

        Discussion discussion = discussionService.findByIdFetchOrNull(0L);

        then(discussion).isNotNull();
    }

    @Test
    @DisplayName("findByIdFetchOrNull_discussion이 없을 경우 예외를 반환한다")
    void findByIdFetchOrNull_withNoDiscussion() {
        given(discussionRepository.findByIdFetchOrNull(any(Long.class))).willReturn(null);

        Throwable throwable = catchThrowable(() -> discussionService.findByIdFetchOrNull(0L));

        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    private CreateDiscussionRequestDto getCreateDiscussionRequestDto() {
        CreateDiscussionRequestDto dto = new CreateDiscussionRequestDto();
        dto.question = "questionTest";
        dto.liveReviewRequired = false;
        dto.liveReviewAvailableTimes = null;
        dto.discussionType = "DIRECT";
        dto.gitRepositoryId = "gitRepositoryId";
        dto.codes = List.of(getCreateDiscussionCodeRequestDto(), getCreateDiscussionCodeRequestDto());
        dto.gitNodeId = "gitNodeId";
        dto.usePriority = true;
        dto.tagIds = List.of(1L, 2L);
        dto.codes = null;
        return dto;
    }

    private CreateDiscussionCodeRequestDto getCreateDiscussionCodeRequestDto() {
        CreateDiscussionCodeRequestDto dto = new CreateDiscussionCodeRequestDto();
        dto.content = "content";
        dto.filename = "filename";
        dto.language = "language";
        return dto;
    }
}
