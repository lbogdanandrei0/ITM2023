package com.itm.api.request;

import com.itm.api.request.model.dto.BreakRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/break-request")
public class BreakRequestController {

    private final BreakRequestService breakRequestService;

    public BreakRequestController(BreakRequestService breakRequestService) {
        this.breakRequestService = breakRequestService;
    }

    @PostMapping
    public ResponseEntity<BreakRequestDTO> addBreakRequest(@RequestBody @Validated(PostMapping.class) BreakRequestDTO breakRequest) {
        return ResponseEntity.ok(breakRequestService.createBreakRequest(breakRequest));
    }

}
