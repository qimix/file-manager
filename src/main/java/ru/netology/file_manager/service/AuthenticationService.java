package ru.netology.file_manager.service;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.file_manager.dto.JwtAuthenticationResponse;
import ru.netology.file_manager.dto.SignInFrontendRequest;
import ru.netology.file_manager.dto.SignUpRequest;
import ru.netology.file_manager.model.Role;
import ru.netology.file_manager.model.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public static final Logger logger = (Logger) LoggerFactory.getLogger(AuthenticationService.class);
    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        logger.info("Start user registration: {}", request);
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        logger.info("User registration success {}", request);
        return new JwtAuthenticationResponse(jwt);
    }
    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse login(SignInFrontendRequest frontendRequest) {
        logger.info("Start user get token: {}", frontendRequest);
        var user = userService.getByEmail(frontendRequest.getEmail());
        if (passwordEncoder.matches(frontendRequest.getPassword(), user.getPassword())) {
            var jwt = jwtService.generateToken(user);
            logger.info("User getting token is success: {}", jwt);
            return new JwtAuthenticationResponse(jwt);
        }
        logger.error("Error getting token for user: {}", frontendRequest);
        throw new UsernameNotFoundException("Bad credentials");
    }

}
