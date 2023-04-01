package com.itm.api.timeline.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class EnhancedUserTimelineDTO {

    private Date startDate;

    private Date endDate;

    private UUID externalUuid;

    private String username;

    private UUID userUuid;

}
