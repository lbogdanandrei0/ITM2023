package com.itm.api.timeline;

import com.itm.api.timeline.model.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    @Query(nativeQuery = true, value = "select * from user_timeline where user_id = :userId and start_date >= cast(:startDate as timestamp) and end_date <= cast(:endDate as timestamp)")
    List<Timeline> findUserTimelinesBetween(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select * from user_timeline where start_date >= cast(:startDate as timestamp) and end_date <= cast(:endDate as timestamp)")
    List<Timeline> findTimelinesBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
