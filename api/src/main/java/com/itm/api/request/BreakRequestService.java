package com.itm.api.request;

import com.itm.api.base.exception.BreakRequestNotFound;
import com.itm.api.base.exception.InvalidUserList;
import com.itm.api.base.exception.UserNotFoundException;
import com.itm.api.request.model.BreakRequest;
import com.itm.api.request.model.dto.BreakRequestDTO;
import com.itm.api.timeline.TimelineService;
import com.itm.api.timeline.model.Timeline;
import com.itm.api.user.UserService;
import com.itm.api.user.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BreakRequestService {

    private final BreakRequestRepository breakRequestRepository;
    private final BreakRequestMapper breakRequestMapper;
    private final UserService userService;
    private final TimelineService timelineService;

    public BreakRequestService(BreakRequestRepository breakRequestRepository, BreakRequestMapper breakRequestMapper, UserService userService, TimelineService timelineService) {
        this.breakRequestRepository = breakRequestRepository;
        this.breakRequestMapper = breakRequestMapper;
        this.userService = userService;
        this.timelineService = timelineService;
    }

    public BreakRequestDTO createBreakRequest(BreakRequestDTO breakRequestDTO) {
        List<Timeline> timelines = breakRequestDTO.getTimelineUuids().stream().map((timelineUuid) -> {
            Optional<Timeline> timeline = timelineService.findByExternalUuid(timelineUuid);
            return timeline.orElse(null);
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (timelines.isEmpty()) {
            throw new InvalidUserList("Break requests require at least 1 invitation");
        }
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> initiator = userService.findUserByUsername(username);
        if (initiator.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        BreakRequest newBreakRequest = breakRequestMapper.breakRequestDTOToBreakRequest(breakRequestDTO);
        newBreakRequest.setTimelines(timelines);
        newBreakRequest.setExternalUuid(UUID.randomUUID());
        newBreakRequest.setInitiator(initiator.get());
        return breakRequestMapper.breakRequestToBreakRequestDTO(breakRequestRepository.save(newBreakRequest));
    }

    public BreakRequestDTO updateBreakRequest(BreakRequestDTO breakRequestDTO) {
        Optional<BreakRequest> existingBreakRequest = breakRequestRepository.findByExternalUuid(breakRequestDTO.getExternalUuid().toString());
        if (existingBreakRequest.isEmpty()) {
            throw new BreakRequestNotFound("Break request not found");
        }
        if (breakRequestDTO.getTimelineUuids() == null) {
            breakRequestMapper.updateBreakRequest(existingBreakRequest.get(), breakRequestDTO);
            return breakRequestMapper.breakRequestToBreakRequestDTO(breakRequestRepository.save(existingBreakRequest.get()));
        }
        List<Timeline> timelines = breakRequestDTO.getTimelineUuids().stream().map((timelineUuid) -> {
            Optional<Timeline> timeline = timelineService.findByExternalUuid(timelineUuid);
            return timeline.orElse(null);
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (timelines.isEmpty()) {
            throw new InvalidUserList("Break requests require at least 1 invitation");
        }
        breakRequestMapper.updateBreakRequest(existingBreakRequest.get(), breakRequestDTO);
        existingBreakRequest.get().setTimelines(timelines);
        return breakRequestMapper.breakRequestToBreakRequestDTO(breakRequestRepository.save(existingBreakRequest.get()));
    }

    public List<BreakRequestDTO> getBreakRequestByUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // TODO: replace findAll
        return breakRequestMapper.breakRequestToBreakRequestDTO(breakRequestRepository.findAll().stream().map(breakRequest -> {
            if (breakRequest.getTimelines() != null && userHadBeenInvited(username, breakRequest.getTimelines())) {
                return breakRequest;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    private boolean userHadBeenInvited(String username, List<Timeline> timelines) {
        return timelines.stream().anyMatch(tl -> tl.getUser() != null && username.equals(tl.getUser().getUsername()));
    }

}
