package com.itm.api.timeline;

import com.itm.api.timeline.model.Timeline;
import com.itm.api.timeline.model.dto.EnhancedUserTimelineDTO;
import com.itm.api.user.model.User;
import com.itm.api.timeline.model.dto.UserTimelineDTO;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TimelineService {

    private final TimelineRepository timelineRepository;
    private final TimelineMapper timelineMapper;

    public TimelineService(TimelineRepository timelineRepository, TimelineMapper timelineMapper) {
        this.timelineRepository = timelineRepository;
        this.timelineMapper = timelineMapper;
    }

    public List<UserTimelineDTO> getTimelines(Long userId) {
        Instant startDate = Instant.now();
        startDate = startDate.atZone(ZoneOffset.UTC).withHour(0).withMinute(0).withSecond(0).toInstant();
        Instant endDate = Instant.now();
        endDate = endDate.atZone(ZoneOffset.UTC).withHour(23).withMinute(59).withSecond(59).toInstant();
        List<Timeline> userTimelines = timelineRepository.findUserTimelinesBetween(startDate.toString(), endDate.toString(), userId);
        return timelineMapper.timelinesToTimelinesDTO(userTimelines);
    }

    public UserTimelineDTO addTimeline(UserTimelineDTO userTimelineDTO, User user) {
        Timeline newTimeline = timelineMapper.timelineDTOToTimeline(userTimelineDTO);
        UUID timelineUid = UUID.randomUUID();
        newTimeline.setUser(user);
        newTimeline.setExternalUuid(timelineUid);
        return timelineMapper.timelineToTimelineDTO(timelineRepository.save(newTimeline));
    }

    public List<EnhancedUserTimelineDTO> getTimelinesBetweenRange(String startDate, String endDate) {
        List<Timeline> timelines = timelineRepository.findTimelinesBetween(startDate.toString(), endDate.toString());
        return timelineMapper.timelinesToEnhancedTimelinesDTO(timelines);
    }

    public Optional<Timeline> findByExternalUuid(String externalUuid) {
        return timelineRepository.findByExternalUuid(externalUuid);
    }

}
