package com.itm.api.timeline;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.itm.api.timeline")
@EnableJpaRepositories(basePackages = "com.itm.api.timeline")
public class TimelineConfiguration {

    @Bean
    public TimelineService timelineService(TimelineRepository timelineRepository, TimelineMapper timelineMapper) {
        return new TimelineService(timelineRepository, timelineMapper);
    }

}
