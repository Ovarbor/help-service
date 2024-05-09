package ru.help.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.help.dto.AuthDtoRequest;
import ru.help.dto.CreateUserDtoRequest;
import ru.help.dto.UserDtoResponse;
import ru.help.security.service.TokenService;
import ru.help.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthRegController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDtoResponse> registrationUser(@Valid @RequestBody CreateUserDtoRequest createUserDtoRequest) {
        log.info("POST: /user/registration");
        return ResponseEntity.status(201).body(userService.addUser(createUserDtoRequest));
    }

    @PostMapping("/authorization")
    public String getToken(@Valid @RequestBody AuthDtoRequest authDtoRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDtoRequest.getUsername(), authDtoRequest.getPassword()));
        log.info("POST: /user/authorization");
        return tokenService.generateToken(authentication);
    }
}
