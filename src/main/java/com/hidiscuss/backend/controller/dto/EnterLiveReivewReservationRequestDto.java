package com.hidiscuss.backend.controller.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class EnterLiveReivewReservationRequestDto {

    @NotNull
    public List<Long> discussionCodeIdList;
}
