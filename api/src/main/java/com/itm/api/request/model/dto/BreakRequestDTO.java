package com.itm.api.request.model.dto;

import com.itm.api.base.validation.PatchValidation;
import com.itm.api.base.validation.PostValidation;
import com.itm.api.timeline.model.Timeline;
import com.itm.api.timeline.model.dto.EnhancedUserTimelineDTO;
import com.itm.api.user.model.User;
import com.itm.api.user.model.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BreakRequestDTO {

    @NotBlank(groups = {PostValidation.class})
    private String place;

    @NotBlank(groups = {PostValidation.class})
    private String comment;

    @Null(groups = {PostValidation.class})
    @NotNull(groups = {PatchValidation.class})
    private UUID externalUuid;

    @Null(groups = {PostValidation.class, PatchValidation.class})
    private UserDTO initiator;

    @NotEmpty(groups = {PostValidation.class})
    private List<String> timelineUuids;

    @Null(groups = {PostValidation.class, PatchValidation.class})
    private List<EnhancedUserTimelineDTO> timelines;

}
