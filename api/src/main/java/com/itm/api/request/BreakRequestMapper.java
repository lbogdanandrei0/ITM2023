package com.itm.api.request;

import com.itm.api.request.model.BreakRequest;
import com.itm.api.request.model.dto.BreakRequestDTO;
import com.itm.api.timeline.model.Timeline;
import com.itm.api.timeline.model.dto.EnhancedUserTimelineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BreakRequestMapper {

    BreakRequest breakRequestDTOToBreakRequest(BreakRequestDTO breakRequestDTO);

    default List<EnhancedUserTimelineDTO> timelineListToEnhancedUserTimelineDTOList(List<Timeline> list) {
        return list.stream().filter(Objects::nonNull).map(timeline -> {
            EnhancedUserTimelineDTO dto = new EnhancedUserTimelineDTO();
            if (timeline.getUser() != null) {
                dto.setUsername(timeline.getUser().getUsername());
                dto.setUserUuid(timeline.getUser().getExternalUuid());
            }
            dto.setExternalUuid(timeline.getExternalUuid());
            dto.setStartDate(timeline.getStartDate());
            dto.setEndDate(timeline.getEndDate());
            return dto;
        }).collect(Collectors.toList());
    }

    @Mapping(target = "timelines", ignore = true)
    void updateBreakRequest(@MappingTarget BreakRequest breakRequest, BreakRequestDTO existingBreakRequest);
    @Mapping(target = "timelines", source = "timelines")
    BreakRequestDTO breakRequestToBreakRequestDTO(BreakRequest breakRequest);

    List<BreakRequestDTO> breakRequestToBreakRequestDTO(List<BreakRequest> breakRequest);
}
