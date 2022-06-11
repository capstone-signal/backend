package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionCodeRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequestFileDetail;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Service
@AllArgsConstructor
@Transactional
public class DiscussionCodeService {
    private final DiscussionCodeRepository discussionCodeRepository;
    private final GithubService githubService;

    public List<DiscussionCode> createFromDirect(Discussion discussion, List<CreateDiscussionCodeRequestDto> dto) {
        List<DiscussionCode> codes = new ArrayList<>();
        dto.forEach(d -> {
            DiscussionCode code = DiscussionCode.builder()
                    .discussion(discussion)
                    .filename(d.filename)
                    .content(d.content)
                    .language(d.language)
                    .build();
            codes.add(code);
        });
        if (codes.size() == 0) {
           throw new EmptyDiscussionCodeException("No codes found");
        }
        return discussionCodeRepository.saveAll(codes);
    }

    public List<DiscussionCode> createFromFiles(Discussion discussion, List<?> files, Long repoId) {
        List<DiscussionCode> codes = new ArrayList<>();
        files.forEach(f -> {
            DiscussionCode.DiscussionCodeBuilder builder = DiscussionCode.builder().discussion(discussion);
            if (f instanceof GHPullRequestFileDetail) {
                GHPullRequestFileDetail file = (GHPullRequestFileDetail) f;
                builder.filename(file.getFilename())
                        .content(githubService.getContent(file.getFilename(), file.getBlobUrl().toString(), repoId))
                        .language(getLanguageFromName(file.getFilename()));

            } else if (f instanceof GHCommit.File) {
                GHCommit.File file = (GHCommit.File) f;
                builder.filename(file.getFileName())
                        .content(githubService.getContent(file.getFileName(), file.getBlobUrl().toString(), repoId))
                        .language(getLanguageFromName(file.getFileName()));
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

    private String getLanguageFromName(String filename) {
        Map<String, String> extLangMap = Map.ofEntries(
                entry("js", "JavaScript"),
                entry("ts", "TypeScript"),
                entry("jsx", "JavaScript"),
                entry("tsx", "TypeScript"),
                entry("java", "Java"),
                entry("py", "Python"),
                entry("c", "C"),
                entry("cs", "C#"),
                entry("cpp", "C++"),
                entry("html", "HTML"),
                entry("xml", "XML"),
                entry("php", "PHP"),
                entry("json", "JSON"),
                entry("md", "Markdown"),
                entry("ps1", "Powershell"),
                entry("rb", "Ruby"),
                entry("css", "CSS"),
                entry("scss", "SCSS"),
                entry("sass", "SASS"),
                entry("r", "R")
        );
        String language = extLangMap.get(filename.substring(filename.lastIndexOf(".") + 1));
        return (language == null) ? "etc" : language;
    }

    public List<DiscussionCode> getDiscussionCode(Discussion discussion) {
        return discussionCodeRepository.findByDiscussion(discussion);
    }
}

