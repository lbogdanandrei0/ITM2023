package com.itm.api.user;

import com.itm.api.timeline.TimelineService;
import com.itm.api.user.model.User;
import com.itm.api.user.model.dto.LogoutDTO;
import com.itm.api.user.model.dto.UserDTO;
import com.itm.api.timeline.model.dto.UserTimelineDTO;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TimelineService timelineService;

    public UserService(UserRepository userRepository, UserMapper userMapper, TimelineService timelineService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.timelineService = timelineService;
    }

    public UserDTO login() {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return userMapper.userToUserDTO(existingUser.get());
        }
        UUID userUid = UUID.randomUUID();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setExternalUuid(userUid);
        User savedUser = userRepository.save(newUser);
        return userMapper.userToUserDTO(savedUser);
    }

    public LogoutDTO logout() {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LogoutDTO logoutDTO = new LogoutDTO();
        logoutDTO.setUsername(username);
        return logoutDTO;
    }

    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser.isEmpty()) {
            return null;
        }
        User providedUser = userMapper.userDTOToUser(userDTO);
        User updatedUser = existingUser.get();
        userMapper.updateUser(updatedUser, providedUser);
        return userMapper.userToUserDTO(userRepository.save(updatedUser));
    }

    public List<UserTimelineDTO> getTimelines() {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.map(user -> timelineService.getTimelines(user.getId())).orElse(null);
    }

    public UserTimelineDTO addTimeline(UserTimelineDTO userTimelineDTO) {
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.map(user -> timelineService.addTimeline(userTimelineDTO, user)).orElse(null);
    }

}

