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
import ru.netology.file_manager.dto.SignUpRequest;
import ru.netology.file_manager.model.Token;
import ru.netology.file_manager.repository.TokenRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class AuthTests {
    private Token token;
    private SignUpRequest signUpRequest;
    private SignInFrontendRequest frontendRequest;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private AuthController authController;

    @BeforeEach
    public void initData() {
        signUpRequest = mock(SignUpRequest.class);
        Mockito.when(signUpRequest.getUsername()).thenReturn("user2");
        Mockito.when(signUpRequest.getPassword()).thenReturn("my_1secret1_password");
        Mockito.when(signUpRequest.getEmail()).thenReturn("user2@gmail.com");

        frontendRequest = mock(SignInFrontendRequest.class);
        Mockito.when(frontendRequest.getEmail()).thenReturn("user@gmail.com");
        Mockito.when(frontendRequest.getPassword()).thenReturn("admin1234");

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

    @Test
    @DisplayName("JUnit test for AuthController.sign-up")
    void testAuthControllerSignUp() {
        JwtAuthenticationResponse authenticationResponse = authController.signUp(signUpRequest);
        assertThat(authenticationResponse).isNotNull();
    }

}
