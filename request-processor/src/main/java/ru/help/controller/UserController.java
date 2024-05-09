package ru.help.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.help.dto.RequestDtoRequest;
import ru.help.dto.UserRequestDtoResponse;
import ru.help.model.User;
import ru.help.service.RequestService;
import ru.help.service.UserService;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final RequestService requestService;
    private final UserService userService;

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserRequestDtoResponse> createRequest(Principal principal,
                                                                @Valid @RequestBody RequestDtoRequest requestDtoRequest) {
        User user = userService.findUserByName(principal.getName());
        log.info("POST: /user/request");
        return ResponseEntity.status(201).body(requestService.createRequest(user.getUserId(), requestDtoRequest));
    }

    @PatchMapping("/request/{requestId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserRequestDtoResponse> updateRequest(Principal principal,
                                                                @PathVariable Long requestId,
                                                                @Valid @RequestBody RequestDtoRequest requestDtoRequest) {
        User user = userService.findUserByName(principal.getName());
        log.info("PATCH: /user/request{}", requestId);
        return ResponseEntity.ok().body(requestService.updateRequest(user.getUserId(), requestId, requestDtoRequest));
    }

    @PatchMapping("/request/{requestId}/send")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<UserRequestDtoResponse> sendRequest(Principal principal, @PathVariable Long requestId) {
        User user = userService.findUserByName(principal.getName());
        log.info("PATCH: /user/request/{}/send", requestId);
        return ResponseEntity.ok().body(requestService.sendRequest(user.getUserId(), requestId));
    }

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<UserRequestDtoResponse>> getRequests(Principal principal,
                                                                    @RequestParam(value = "from", defaultValue = "0")
                                                                @PositiveOrZero Integer from,
                                                                    @RequestParam(value = "size", defaultValue = "5")
                                                                @Positive Integer size,
                                                                    @RequestParam(value = "sort", defaultValue = "asc")
                                                                    String sortType) {
        User user = userService.findUserByName(principal.getName());
        log.info("GET: /user/request/?from={}&size={}&sort={}", from, size, sortType);
        return ResponseEntity.ok().body(requestService.getRequests(user.getUserId(), from, size, sortType));
    }
}
