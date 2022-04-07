package com.hidiscuss.backend.controller.dto;

public class CreateDiscussionCodeRequestDto {
    public String filename;
    public String content;
    public int getLength() {
        return content.length();
    }
}
