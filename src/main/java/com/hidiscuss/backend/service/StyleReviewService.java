package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateCommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.DiscussionCodeDto;
import com.hidiscuss.backend.entity.DiscussionCode;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StyleReviewService {

    public List<CreateCommentReviewDiffDto> createStyleReviewDto(List<DiscussionCode> codes) {
        List<CreateCommentReviewDiffDto> dtos = new ArrayList<>();
        for(DiscussionCode code : codes) {
            List<Map<String, String>> diffs = executeLint(code.getContent());
            for(Map<String, String> diff : diffs) {
                String comment = diff.get("line") + "번째 줄 " + diff.get("column") + "번째 위치에 " + diff.get("msg") + " 문제가 발생했습니다. (" + diff.get("msg_id") + " : " + diff.get("symbol") + ")";
                String codeLocate = getCodeLocate(code.getContent(), diff);
                CreateCommentReviewDiffDto dto = CreateCommentReviewDiffDto
                        .builder()
                        .discussionCode(DiscussionCodeDto.fromEntity(code))
                        .comment(comment)
                        .codeAfter("")
                        .codeLocate(codeLocate)
                        .build();
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private String getCodeLocate(String code, Map<String, String> diff) {
        Integer line = Integer.parseInt(diff.get("line"));
        Integer column = Integer.parseInt(diff.get("column"));
        Integer sum = 0;
        
        String byLine[] = code.split("\n");
        for (int i = 0; i < line - 1; i++) {
            sum += byLine[i].length() + 1;
        }
        sum += column;
        
        return sum + "," + (sum + 1);
    }

    private List<Map<String, String>> executeLint(String content) {
        List<Map<String, String>> output = new ArrayList<>();
        try {
            ProcessBuilder source = new ProcessBuilder("bash", "-c", "echo \"" + content + "\" | pylint --from-stdin hidiscuss --msg-template='%:{line}:{column}:{msg}:{msg_id}:{symbol}'");
            Process p = source.start();
            BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = stdout.readLine()) != null) {
                if (line.length() != 0 && line.charAt(0) == '%') {
                    String getStr[] = line.split(":");
                    Map<String, String> map = new HashMap<>();
                    map.put("line", getStr[1]);
                    map.put("column", getStr[2]);
                    map.put("msg", getStr[3]);
                    map.put("msg_id", getStr[4]);
                    map.put("symbol", getStr[5]);
                    output.add(map);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
