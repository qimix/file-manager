package ru.netology.file_manager;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import ru.netology.file_manager.controller.AuthController;
import ru.netology.file_manager.dto.JwtAuthenticationResponse;
import ru.netology.file_manager.dto.SignInFrontendRequest;
import ru.netology.file_manager.model.Token;
import ru.netology.file_manager.repository.TokenRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class AuthTests {
    private SignInFrontendRequest frontendRequest;
    private Token token;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private AuthController authController;

    @BeforeEach
    public void initData() {
        frontendRequest = mock(SignInFrontendRequest.class);
        Mockito.when(frontendRequest.getEmail()).thenReturn("admin@gmail.com");
        Mockito.when(frontendRequest.getPassword()).thenReturn("$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha");
        token = Token.builder()
                .setToken("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJlbWFpbCI6ImFkbWluQGdtYWlsLmNvbSIsInN1YiI6ImFkbWluIiwiaWF0IjoxNzM1MTIzNDcyLCJleHAiOjE3MzUyNjc0NzJ9.Gums_M7BEL_QJxwEvqibId7sj6eXah49SSqeGe8Ju98")
                .build();
    }

    @Test
    @DisplayName("JUnit test for AuthController.login")
    void testAuthControllerLogin() {
        JwtAuthenticationResponse authenticationResponse = authController.login(frontendRequest);
        assertThat(authenticationResponse).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for AuthController.logout")
    void testAuthControllerLogout() {
        tokenRepository.save(token);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getHeader("auth-token")).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJlbWFpbCI6ImFkbWluQGdtYWlsLmNvbSIsInN1YiI6ImFkbWluIiwiaWF0IjoxNzM1MTIzNDcyLCJleHAiOjE3MzUyNjc0NzJ9.Gums_M7BEL_QJxwEvqibId7sj6eXah49SSqeGe8Ju98");
        ResponseEntity<String> responseEntity = authController.logout(httpServletRequest);
        assertThat(responseEntity).isNotNull();
    }

}
