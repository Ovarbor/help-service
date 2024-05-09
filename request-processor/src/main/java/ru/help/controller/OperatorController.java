package ru.help.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.help.dto.OperatorRequestDtoResponse;
import ru.help.service.RequestService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/operator")
public class OperatorController {

    private final RequestService requestService;

    @PatchMapping("/requests/{requestId}/accept")
    @PreAuthorize("hasAuthority('SCOPE_OPERATOR')")
    public ResponseEntity<OperatorRequestDtoResponse> acceptRequest(@PathVariable Long requestId) {
        log.info("PATCH: /request/{}/accept", requestId);
        return ResponseEntity.ok().body(requestService.acceptRequest(requestId));
    }

    @PatchMapping("/requests/{requestId}/denied")
    @PreAuthorize("hasAuthority('SCOPE_OPERATOR')")
    public ResponseEntity<OperatorRequestDtoResponse> deniedRequest(@PathVariable Long requestId) {
        log.info("PATCH: /request/{}/denied", requestId);
        return ResponseEntity.ok().body(requestService.deniedRequest(requestId));
    }

    @GetMapping("/requests")
    @PreAuthorize("hasAuthority('SCOPE_OPERATOR')")
    public ResponseEntity<List<OperatorRequestDtoResponse>> getAllRequests(@RequestParam(value = "from", defaultValue = "0")
                                                                   @PositiveOrZero Integer from,
                                                                   @RequestParam(value = "size", defaultValue = "5")
                                                                   @Positive Integer size,
                                                                   @RequestParam(value = "sort", defaultValue = "asc")
                                                                   String sortType) {
        log.info("GET: /operator/requests?from={}&size={}&sort={}",  from, size, sortType);

        return ResponseEntity.ok().body(requestService.getAllRequests(from, size, sortType));
    }

    @GetMapping("/user/requests")
    @PreAuthorize("hasAuthority('SCOPE_OPERATOR')")
    public ResponseEntity<List<OperatorRequestDtoResponse>> getAllUserRequests(@RequestParam(value = "username", required = false) String username,
                                                                       @RequestParam(value = "from", defaultValue = "0")
                                                                       @PositiveOrZero Integer from,
                                                                       @RequestParam(value = "size", defaultValue = "5")
                                                                           @Positive Integer size,
                                                                       @RequestParam(value = "sort", defaultValue = "asc")
                                                                           String sortType) {
        log.info("GET: /operator/user/requests?username={}&from={}&size={}&sort={}", username, from, size, sortType);
        return ResponseEntity.ok().body(requestService.getAllUserRequests(username, from, size, sortType));

    }
}
