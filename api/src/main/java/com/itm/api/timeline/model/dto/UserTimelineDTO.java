package com.itm.api.timeline.model.dto;

import com.itm.api.base.validation.PostValidation;
import com.itm.api.base.validation.TimelineIntervalConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@TimelineIntervalConstraint(groups = {PostValidation.class})
public class UserTimelineDTO {

    @NotNull(groups = {PostValidation.class})
    private Date startDate;

    @NotNull(groups = {PostValidation.class})
    private Date endDate;

    @Null(groups = {PostValidation.class})
    private UUID externalUuid;

}
