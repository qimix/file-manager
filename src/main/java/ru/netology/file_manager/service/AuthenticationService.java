package ru.netology.file_manager.service;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.file_manager.dto.JwtAuthenticationResponse;
import ru.netology.file_manager.dto.SignInFrontendRequest;
import ru.netology.file_manager.dto.SignInRequest;
import ru.netology.file_manager.dto.SignUpRequest;
import ru.netology.file_manager.model.Role;
import ru.netology.file_manager.model.User;
import ru.netology.file_manager.utils.FileManager;

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

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse login(SignInFrontendRequest frontendRequest) {
        var user = userService.getByEmail(frontendRequest.getEmail());
        if (passwordEncoder.matches(frontendRequest.getPassword(), user.getPassword())) {
            var jwt = jwtService.generateToken(user);
            return new JwtAuthenticationResponse(jwt);
        }
        throw new UsernameNotFoundException("Bad credentials");
    }

}
