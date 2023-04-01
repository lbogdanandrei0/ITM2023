package com.itm.api.user;

import com.itm.api.base.validation.PatchValidation;
import com.itm.api.base.validation.PostValidation;
import com.itm.api.user.model.dto.LogoutDTO;
import com.itm.api.user.model.dto.UserDTO;
import com.itm.api.timeline.model.dto.UserTimelineDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login() {
        return ResponseEntity.ok(userService.login());
    }

    @PatchMapping("/save-profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody @Validated(PatchValidation.class) UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    @GetMapping("/logout")
    public ResponseEntity<LogoutDTO> logout() {
        return ResponseEntity.ok(userService.logout());
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<UserTimelineDTO>> getTimeline() {
        return ResponseEntity.ok(userService.getTimelines());
    }

    @PostMapping("/add-timeline")
    public ResponseEntity<UserTimelineDTO> addTimeline(@RequestBody @Validated(PostValidation.class) UserTimelineDTO userTimelineDTO) {
        return ResponseEntity.ok(userService.addTimeline(userTimelineDTO));
    }

}
