package ru.netology.file_manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.file_manager.controller.AuthController;
import ru.netology.file_manager.dto.JwtAuthenticationResponse;
import ru.netology.file_manager.dto.SignInFrontendRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class AuthTests {
    private SignInFrontendRequest frontendRequest;
    @Autowired
    private AuthController authController;

    @BeforeEach
    public void initData() {
        frontendRequest = mock(SignInFrontendRequest.class);
        Mockito.when(frontendRequest.getEmail()).thenReturn("admin@gmail.com");
        Mockito.when(frontendRequest.getPassword()).thenReturn("$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha");
    }

    @Test
    @DisplayName("JUnit test for AuthController.login")
    void testAuthController() {
        JwtAuthenticationResponse authenticationResponse = authController.login(frontendRequest);
        assertThat(authenticationResponse).isNotNull();
    }

}
