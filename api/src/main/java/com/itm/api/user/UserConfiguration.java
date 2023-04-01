package com.itm.api.user;


import com.itm.api.timeline.TimelineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.itm.api.user")
@EnableJpaRepositories(basePackages = "com.itm.api.user")
public class UserConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository, UserMapper userMapper, TimelineService timelineService) {
        return new UserService(userRepository, userMapper, timelineService);
    }

}
