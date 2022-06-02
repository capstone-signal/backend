package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateDiscussionCodeRequestDto;
import com.hidiscuss.backend.entity.Discussion;
import com.hidiscuss.backend.entity.DiscussionCode;
import com.hidiscuss.backend.exception.EmptyDiscussionCodeException;
import com.hidiscuss.backend.repository.DiscussionCodeRepository;
import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequestFileDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public List<DiscussionCode> createFromFiles(Discussion discussion, List<?> files) {
        List<DiscussionCode> codes = new ArrayList<>();
        files.forEach(f -> {
            DiscussionCode.DiscussionCodeBuilder builder = DiscussionCode.builder().discussion(discussion);
            if (f instanceof GHPullRequestFileDetail) {
                GHPullRequestFileDetail file = (GHPullRequestFileDetail) f;
                builder.filename(file.getFilename())
                        .content(getContentFromUrl(file.getBlobUrl()))
                        .language(getLanguageFromName(file.getFilename()));

            } else if (f instanceof GHCommit.File) {
                GHCommit.File file = (GHCommit.File) f;
                builder.filename(file.getFileName())
                        .content(getContentFromUrl(file.getBlobUrl()))
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

    String getContentFromUrl(URL url) {
        RestTemplate restTemplate = new RestTemplate();
        String urlStr = url.toString();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlStr, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new RuntimeException();
        responseEntity.//jsoup으로 테이블을 가져와야 할 판...
        return responseEntity.getBody();
    }
}

