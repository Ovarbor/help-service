package ru.help.controller;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.help.dto.UserDtoResponse;
import ru.help.service.UserService;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserDtoResponse>> getUsers(@RequestParam(value = "from", defaultValue = "0")
                                                          @PositiveOrZero Integer from,
                                                          @RequestParam(value = "size", defaultValue = "5")
                                                              @Positive Integer size) {
        log.info("GET: /admin/users?from={}&size={}", from, size);
        return ResponseEntity.ok().body(userService.findAllUsers(from, size));
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserDtoResponse>> searchUsers(@RequestParam(value = "text", required = false) String text,
                                                             @RequestParam(value = "from", defaultValue = "0")
                                                                 @PositiveOrZero Integer from,
                                                             @RequestParam(value = "size", defaultValue = "5")
                                                                  @Positive Integer size) {
        log.info("GET: /admin/search?text={}&from={}&size={}", text, from, size);
        return ResponseEntity.ok().body(userService.searchUsers(text, from, size));
    }

    @PatchMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserDtoResponse> assignAuthority(@PathVariable Long userId) {
        log.info("PATCH: /admin/{}", userId);
        return ResponseEntity.ok().body(userService.assignAuthority(userId));
    }
}
