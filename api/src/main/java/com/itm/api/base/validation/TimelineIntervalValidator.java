package com.itm.api.base.validation;

import com.itm.api.timeline.model.dto.UserTimelineDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

public class TimelineIntervalValidator implements ConstraintValidator<TimelineIntervalConstraint, UserTimelineDTO> {
    @Override
    public boolean isValid(UserTimelineDTO value, ConstraintValidatorContext context) {
        return !value.getStartDate().after(value.getEndDate()) && sameDay(value.getStartDate()) && sameDay(value.getEndDate());
    }

    private boolean sameDay(Date date) {
        Instant startDate = Instant.now();
        startDate = startDate.atZone(ZoneOffset.UTC).withHour(0).withMinute(0).withSecond(0).toInstant();
        Instant endDate = Instant.now();
        endDate = endDate.atZone(ZoneOffset.UTC).withHour(23).withMinute(59).withSecond(59).toInstant();
        return date.toInstant().isAfter(startDate) && date.toInstant().isBefore(endDate);
    }
}
