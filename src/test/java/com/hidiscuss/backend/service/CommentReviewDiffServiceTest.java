package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.CommentReviewResponseDto;
import com.hidiscuss.backend.controller.dto.CreateCommentReviewRequestDto;
import com.hidiscuss.backend.controller.dto.DiscussionCodeDto;
import com.hidiscuss.backend.entity.*;
import com.hidiscuss.backend.repository.CommentReviewDiffRepository;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import com.hidiscuss.backend.repository.ReviewRepository;
import com.hidiscuss.backend.repository.ReviewThreadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentReviewDiffServiceTest {

    @Mock private CommentReviewDiffRepository commentReviewDiffRepository;
    @Mock private DiscussionCodeRepository discussionCodeRepository;
    @InjectMocks private CommentReviewDiffService commentReviewDiffService;

    private User user;

    private ReviewType reviewType = ReviewType.COMMENT;

    private List<CommentReviewDiffDto> diffList;

    private List<CommentReviewDiff> entityList;

    private Review review;

    private DiscussionCode discussionCode_1;
    private DiscussionCode discussionCode_2;

    @BeforeEach
    void setUp() {
        user = new User();
        review = Review.builder().id(1L).diffList(new ArrayList<>()).build();
        discussionCode_1 = DiscussionCode.builder().id(1L).build();
        discussionCode_2 = DiscussionCode.builder().id(2L).build();
        diffList = List.of(
                getCommentReviewDiffDto(discussionCode_1),
                getCommentReviewDiffDto(discussionCode_2)
        );
        entityList = List.of(
                CommentReviewDiffDto.toEntity(diffList.get(0), review, discussionCode_1),
                CommentReviewDiffDto.toEntity(diffList.get(1), review, discussionCode_2)
        );
    }

    @Test
    @DisplayName("saveCommentReviewDiff_commentReviewDiff가 저장된다.")
    void saveCommentReviewDiff_common() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);

        given(discussionCodeRepository.findByIdFetchJoin(discussionCode_1.getId())).willReturn(Optional.of(discussionCode_1));
        given(discussionCodeRepository.findByIdFetchJoin(discussionCode_2.getId())).willReturn(Optional.of(discussionCode_2));
        given(commentReviewDiffRepository.save(any(CommentReviewDiff.class))).willAnswer(i -> i.getArgument(0));

        review = commentReviewDiffService.saveCommentReviewDiff(dto, review);

        then(CommentReviewDiffDto.fromEntity(review.getDiffList().get(0)).getDiscussionCode().getId()).isEqualTo(diffList.get(0).getDiscussionCode().getId());
        then(CommentReviewDiffDto.fromEntity(review.getDiffList().get(1)).getDiscussionCode().getId()).isEqualTo(diffList.get(1).getDiscussionCode().getId());
        then(review.getDiffList().size()).isEqualTo(diffList.size());
    }

    @Test
    @DisplayName("saveCommentReviewDiff_discussionCode가 없을 경우 예외를 반환한다.")
    void saveCommentReviewDiff_withNoDiscussionCode() {
        CreateCommentReviewRequestDto dto = new CreateCommentReviewRequestDto(1L, diffList);
        Review review = new Review();
        given(discussionCodeRepository.findByIdFetchJoin(any(Long.class))).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> commentReviewDiffService.saveCommentReviewDiff(dto, review));

        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    private CommentReviewDiffDto getCommentReviewDiffDto(DiscussionCode discussionCode) {
        DiscussionCodeDto dto = DiscussionCodeDto.fromEntity(discussionCode);
        return new CommentReviewDiffDto(dto, "codeAfter", "codeLocate", "comment");
    }
}
