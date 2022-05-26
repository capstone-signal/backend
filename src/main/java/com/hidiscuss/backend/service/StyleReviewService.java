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
        List<CreateCommentReviewDiffDto> dtos = new ArrayList<>();

        for(DiscussionCode code : codes) {
            List<String> diffs = executeLint(code.getContent());
            for(String diff : diffs) {
                CreateCommentReviewDiffDto dto = CreateCommentReviewDiffDto
                        .builder()
                        .discussionCode(DiscussionCodeDto.fromEntity(code))
                        .comment(diff)
                        .codeAfter("")
                        .codeLocate("")
                        .build();
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private List<String> executeLint(String content) {
        List<String> output = new ArrayList<>();
        content = "import requests\n" +
                "from selenium import webdriver\n" +
                "import pandas as pd\n" +
                "import time\n" +
                "\n" +
                "options = webdriver.ChromeOptions()\n" +
                "options.add_experimental_option(\"excludeSwitches\", [\"enable-logging\"])\n" +
                "\n" +
                "chromedriver_dir ='C://Blahablah//chromedriver.exe'\n" +
                "driver = webdriver.Chrome(chromedriver_dir, options = options)\n" +
                "\n" +
                "# move to webtoon page\n" +
                "url = 'https://www.naver.com/'\n" +
                "driver.get(url)\n" +
                "time.sleep(0.5)";
        try {
            Process process = Runtime.getRuntime().exec("echo '"+content+"' | pylint --from-stdin hidiscuss --msg-template='% {line}번째 줄 {column}번째 위치에 {msg} 문제가 발생했습니다. ({msg_id}: {symbol})'");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '%')
                    output.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
