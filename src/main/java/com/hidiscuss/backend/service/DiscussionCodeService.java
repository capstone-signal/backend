package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionCodeRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.entity.Status;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
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
        return discussionCodeRepository.saveAll(codes); // TEST TODO : bulk save
    }

    public List<DiscussionCode> createFromCommit(Discussion discussion, GHCommit commit, List<GHCommit.File> files) {
        List<DiscussionCode> codes = new ArrayList<>();
        files.forEach(f -> {
            DiscussionCode code = DiscussionCode.builder()
                    .discussion(discussion)
                    .filename(f.getFileName())
                    .content(f.getPatch())
                    .sha(f.getSha())
                    .status(Status.convertFromGithubStatus(f.getStatus()))
                    .additions((long) f.getLinesAdded())
                    .deletions((long) f.getLinesDeleted())
                    .changes((long) f.getLinesChanged())
                    .build();
        });
        return discussionCodeRepository.saveAll(codes); // TEST TODO : bulk save
    }


}

