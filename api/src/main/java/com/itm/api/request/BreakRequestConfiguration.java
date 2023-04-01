package com.itm.api.request;

import com.itm.api.timeline.TimelineService;
import com.itm.api.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.itm.api.request")
@EnableJpaRepositories(basePackages = "com.itm.api.request")
public class BreakRequestConfiguration {

    @Bean
    public BreakRequestService breakRequestService(BreakRequestRepository breakRequestRepository, BreakRequestMapper breakRequestMapper, UserService userService, TimelineService timelineService) {
        return new BreakRequestService(breakRequestRepository, breakRequestMapper, userService, timelineService);
    }

}
