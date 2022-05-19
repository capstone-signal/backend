package com.hidiscuss.backend.service;

import com.hidiscuss.backend.controller.dto.CreateCommentReviewDiffDto;
import com.hidiscuss.backend.controller.dto.DiscussionCodeDto;
import com.hidiscuss.backend.entity.DiscussionCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StyleReviewService {

    public List<CreateCommentReviewDiffDto> createStyleReviewDto(List<DiscussionCode> codes) {
        List<CreateCommentReviewDiffDto> dto = new ArrayList<>();

        // 파일 생성
        for(int i = 0; i < codes.size(); i++) {
            createFile(codes.get(i).getContent());
            StringBuilder sb = executeLint();
            dto.addAll(createDiffs(sb));
        }

        return dto.stream()
                .map((i) -> CreateCommentReviewDiffDto
                        .builder()
                        .discussionCode(DiscussionCodeDto.fromEntity(i))
                        .comment("pypy")
                        .codeAfter("afaf")
                        .codeLocate("lolo")
                        .build()
                ).collect(Collectors.toList());
    }

    private void createFile(String code) {
        try {
            // 1. 파일 객체 생성
            File file = new File("src/main/python/test.py");
            // 2. 파일 존재여부 체크 및 생성
            if (!file.exists()) {
                file.createNewFile();
            }
            // 3. Buffer를 사용해서 File에 write할 수 있는 BufferedWriter 생성
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            // 4. 파일에 쓰기
            writer.write(code);
            // 5. BufferedWriter close
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder executeLint() {
        // 린트 수행
        StringBuilder output = new StringBuilder();
        try {
            // 1. Run script
            Process process = Runtime.getRuntime().exec("pylint src/main/python/test.py");
            // 2. Read output
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            // 3. 저장
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 4. 테스트용 출력
        return output;
    }

    private List<CreateCommentReviewDiffDto> createDiffs(StringBuilder sb) {
        // string으로 dto 생성 로직
    }
}
