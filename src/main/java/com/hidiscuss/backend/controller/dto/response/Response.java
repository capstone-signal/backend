package com.hidiscuss.backend.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {
    private int status;
    private String success;
    private String message;
}
