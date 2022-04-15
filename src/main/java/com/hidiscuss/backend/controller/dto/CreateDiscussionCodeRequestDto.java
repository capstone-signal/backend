package com.hidiscuss.backend.controller.dto;

import com.hidiscuss.backend.entity.DiscussionCode;

public class CreateDiscussionCodeRequestDto {
    public String filename;
    public String content;

    public int getLength() {
        return content.length();
    }
}
