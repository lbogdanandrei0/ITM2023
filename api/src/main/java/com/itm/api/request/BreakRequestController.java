package com.itm.api.request;

import com.itm.api.base.validation.PostValidation;
import com.itm.api.request.model.dto.BreakRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/break-request")
public class BreakRequestController {

    private final BreakRequestService breakRequestService;

    public BreakRequestController(BreakRequestService breakRequestService) {
        this.breakRequestService = breakRequestService;
    }

    @PostMapping
    public ResponseEntity<BreakRequestDTO> addBreakRequest(@RequestBody @Validated(PostValidation.class) BreakRequestDTO breakRequest) {
        return ResponseEntity.ok(breakRequestService.createBreakRequest(breakRequest));
    }

    @PatchMapping
    public ResponseEntity<BreakRequestDTO> updateBreakRequest(@RequestBody @Validated(PatchMapping.class) BreakRequestDTO breakRequestDTO) {
        return ResponseEntity.ok(breakRequestService.updateBreakRequest(breakRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<List<BreakRequestDTO>> getBreakRequests() {
        return ResponseEntity.ok(breakRequestService.getBreakRequestByUser());
    }

}
