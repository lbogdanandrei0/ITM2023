package com.itm.api.request;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.itm.api.request")
@EnableJpaRepositories(basePackages = "com.itm.api.request")
public class BreakRequestConfiguration {

    @Bean
    public BreakRequestService breakRequestService(BreakRequestRepository breakRequestRepository) {
        return new BreakRequestService(breakRequestRepository);
    }

}
