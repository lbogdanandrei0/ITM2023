package com.itm.api.timeline;

import com.itm.api.timeline.model.dto.EnhancedUserTimelineDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

    private final TimelineService timelineService;

    public TimelineController(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @GetMapping(value = "/range")
    public ResponseEntity<List<EnhancedUserTimelineDTO>> getBetweenRange(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(timelineService.getTimelinesBetweenRange(startDate, endDate));
    }

}
