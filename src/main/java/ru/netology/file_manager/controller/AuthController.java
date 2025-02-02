package ru.netology.file_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.file_manager.dto.JwtAuthenticationResponse;
import ru.netology.file_manager.dto.SignInFrontendRequest;
import ru.netology.file_manager.dto.SignUpRequest;
import ru.netology.file_manager.exception.AuthenticationUserException;
import ru.netology.file_manager.repository.TokenRepository;
import ru.netology.file_manager.service.AuthenticationService;
import ru.netology.file_manager.service.JwtService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authorization")
public class AuthController {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "User authorization")
    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody @Valid SignInFrontendRequest frontendRequest) throws AuthenticationUserException {
        try {
            return authenticationService.login(frontendRequest);
        }catch (Exception e) {
            throw new AuthenticationUserException(e);
        }
    }

    @Operation(summary = "User logout")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        jwtService.dropToken(httpServletRequest.getHeader("auth-token"));
        return new ResponseEntity<String>("Logout user", HttpStatus.OK);
    }

    @Operation(summary = "User registration")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

}
