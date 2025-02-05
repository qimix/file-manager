package ru.netology.file_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static ru.netology.file_manager.utils.LogConstants.LOG_SEPARATOR;
import static ru.netology.file_manager.utils.LogConstants.REQUEST_BODY;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authorization")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "User authorization")
    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody @Valid SignInFrontendRequest frontendRequest) throws AuthenticationUserException {
        logger.info("Start login:");
        logger.info(REQUEST_BODY, frontendRequest);
        logger.info(LOG_SEPARATOR);
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.login(frontendRequest);
            logger.info("Login successful");
            return jwtAuthenticationResponse;
        } catch (Exception e) {
            logger.error("Error login for:");
            logger.error(REQUEST_BODY, frontendRequest, e.getMessage());
            logger.info(LOG_SEPARATOR);
            throw new AuthenticationUserException(e);
        }
    }

    @Operation(summary = "User logout")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        logger.info("Start delete token:");
        logger.info(LOG_SEPARATOR);
        jwtService.dropToken(httpServletRequest.getHeader("auth-token"));
        logger.info("Logout completed");
        return new ResponseEntity<String>("Logout user", HttpStatus.OK);
    }

    @Operation(summary = "User registration")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        logger.info("Start signup:");
        logger.info("Email {}: ", "Username {}: ", "Password {}: ", request.getEmail(), request.getUsername(), request.getPassword());
        logger.info(LOG_SEPARATOR);
        return authenticationService.signUp(request);
    }

}
