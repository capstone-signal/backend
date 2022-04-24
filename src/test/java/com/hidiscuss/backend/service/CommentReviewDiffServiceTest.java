package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.DiscussionCodeDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.CommentReviewDiffRepository;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentReviewDiffServiceTest {

    @Mock
    private CommentReviewDiffRepository commentReviewDiffRepository;
    @Mock
    private DiscussionCodeRepository discussionCodeRepository;
    @InjectMocks
    private CommentReviewDiffService commentReviewDiffService;

    private User user;
    private Review review;
    private ReviewType reviewType = ReviewType.COMMENT;

    private List<CommentReviewDiffDto> dtoList;
    private List<DiscussionCode> codeList;
    private List<Long> idList;

    private DiscussionCode discussionCode_1;
    private DiscussionCode discussionCode_2;
    private CreateCommentReviewRequestDto dto;

    @BeforeEach
    void setUp() {
        user = new User();
        review = Review.builder().id(1L).commentDiffList(new ArrayList<>()).build();
        discussionCode_1 = DiscussionCode.builder().id(1L).build();
        discussionCode_2 = DiscussionCode.builder().id(2L).build();
        dtoList = List.of(
                getCommentReviewDiffDto(discussionCode_1),
                getCommentReviewDiffDto(discussionCode_2)
        );
        codeList = List.of(
                discussionCode_1,
                discussionCode_2
        );
        idList = List.of(
                discussionCode_1.getId(),
                discussionCode_2.getId()
        );
        dto = new CreateCommentReviewRequestDto(1L, dtoList);
    }

    @Test
    @DisplayName("saveCommentReviewDiff_commentReviewDiff가 저장된다.")
    void createCommentReviewDiff_common() {
        given(discussionCodeRepository.findByIdListFetch(idList)).willReturn(codeList);
        given(commentReviewDiffRepository.saveAll(anyList())).willAnswer(i -> i.getArgument(0));

        List<CommentReviewDiff> entityList = commentReviewDiffService.createCommentReviewDiff(review, dtoList);

        then(entityList.get(0).getDiscussionCode().getId()).isEqualTo(discussionCode_1.getId());
        then(entityList.get(1).getDiscussionCode().getId()).isEqualTo(discussionCode_2.getId());
        then(entityList.size()).isEqualTo(dtoList.size());
    }

    @Test
    @DisplayName("saveCommentReviewDiff_discussionCode 개수만큼 만들어지지 않은 경우 예외를 반환한다.")
    void createCommentReviewDiff_withNoDiscussionCode() {
        Review review = new Review();
        given(discussionCodeRepository.findByIdListFetch(idList)).willReturn(new ArrayList<>());

        Throwable throwable = catchThrowable(() -> commentReviewDiffService.createCommentReviewDiff(review, dtoList));

        then(throwable).isInstanceOf(EmptyDiscussionCodeException.class);
    }

    private CommentReviewDiffDto getCommentReviewDiffDto(DiscussionCode discussionCode) {
        DiscussionCodeDto dto = DiscussionCodeDto.fromEntity(discussionCode);
        return new CommentReviewDiffDto(dto, "codeAfter", "codeLocate", "comment");
    }
}
