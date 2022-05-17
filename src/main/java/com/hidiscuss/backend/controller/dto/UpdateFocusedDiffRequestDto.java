package com.hidiscuss.backend.controller.dto;

import javax.validation.constraints.NotNull;

public class UpdateFocusedDiffRequestDto {

    @NotNull
    public String codeAfter;
}
