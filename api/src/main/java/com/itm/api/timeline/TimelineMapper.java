package com.itm.api.timeline;

import com.itm.api.timeline.model.Timeline;
import com.itm.api.timeline.model.dto.EnhancedUserTimelineDTO;
import com.itm.api.timeline.model.dto.UserTimelineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TimelineMapper {

    UserTimelineDTO timelineToTimelineDTO(Timeline timeline);
    List<UserTimelineDTO> timelinesToTimelinesDTO(List<Timeline> timelines);

    Timeline timelineDTOToTimeline(UserTimelineDTO userTimelineDTO);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "userUuid", source = "user.externalUuid")
    EnhancedUserTimelineDTO timelineToEnhancedTimelineDTO(Timeline timeline);

    List<EnhancedUserTimelineDTO> timelinesToEnhancedTimelinesDTO(List<Timeline> timelines);
}
