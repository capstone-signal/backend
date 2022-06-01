package com.hidiscuss.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


public class CompleteDiscussionRequestDto {

    @NotNull
    @NotEmpty
    public List<Long> reviewIds;
}
