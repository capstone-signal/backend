package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionCodeRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Status;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestFileDetail;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DiscussionCodeService {
    private final DiscussionCodeRepository discussionCodeRepository;

    public List<DiscussionCode> createFromDirect(Discussion discussion, List<CreateDiscussionCodeRequestDto> dto) {
        List<DiscussionCode> codes = new ArrayList<>();
        dto.forEach(d -> {
            DiscussionCode code = DiscussionCode.builder()
                    .discussion(discussion)
                    .filename(d.filename)
                    .content(d.content)
                    .sha("DIRECT")
                    .status(Status.ADDED)
                    .additions((long) d.getLength())
                    .deletions(0L)
                    .changes(0L)
                    .build();
            codes.add(code);
        });
        if (codes.size() == 0) {
           throw new EmptyDiscussionCodeException("No codes found");
        }
        return discussionCodeRepository.saveAll(codes); // TEST TODO : bulk save
    }

    public List<DiscussionCode> createFromFiles(Discussion discussion, List<?> files) {
        List<DiscussionCode> codes = new ArrayList<>();
        files.forEach(f -> {
            DiscussionCode.DiscussionCodeBuilder builder = DiscussionCode.builder().discussion(discussion);
            if (f instanceof GHPullRequestFileDetail) {
                GHPullRequestFileDetail file = (GHPullRequestFileDetail) f;
                builder.filename(file.getFilename())
                        .content(file.getPatch())
                        .sha(file.getSha())
                        .status(Status.convertFromGithubStatus(file.getStatus()))
                        .additions((long) file.getAdditions())
                        .deletions((long) file.getDeletions())
                        .changes((long) file.getChanges());

            } else if (f instanceof GHCommit.File) {
                GHCommit.File file = (GHCommit.File) f;
                builder.filename(file.getFileName())
                        .content(file.getPatch())
                        .sha(file.getSha())
                        .status(Status.convertFromGithubStatus(file.getStatus()))
                        .additions((long) file.getLinesAdded())
                        .deletions((long) file.getLinesDeleted())
                        .changes((long) file.getLinesChanged());
            } else {
                throw new IllegalArgumentException("Unknown file type");
            }
            DiscussionCode discussionCode = builder.build();
            if (discussionCode.getContent() != null) {
                codes.add(discussionCode);
            }
        });
        if (codes.size() == 0) {
            throw new EmptyDiscussionCodeException("No codes found");
        }
        return discussionCodeRepository.saveAll(codes);
    }
}

