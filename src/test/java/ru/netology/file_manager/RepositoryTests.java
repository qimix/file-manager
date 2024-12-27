package ru.netology.file_manager;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.netology.file_manager.model.FileInfo;
import ru.netology.file_manager.model.Role;
import ru.netology.file_manager.model.Token;
import ru.netology.file_manager.model.User;
import ru.netology.file_manager.repository.FileInfoRepository;
import ru.netology.file_manager.repository.TokenRepository;
import ru.netology.file_manager.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@Transactional
class RepositoryTests {
    private FileInfo fileInfo;
    private User user;
    private Token token;
    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void initData(){
        fileInfo = FileInfo.builder()
                .setName("test")
                .setSize(999L)
                .setKeyFile("test")
                .setLocalDate(LocalDate.now())
                .build();
        token = Token.builder()
                .setToken("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJlbWFpbCI6ImFkbWluQGdtYWlsLmNvbSIsInN1YiI6ImFkbWluIiwiaWF0IjoxNzM1MTIzNDcyLCJleHAiOjE3MzUyNjc0NzJ9.Gums_M7BEL_QJxwEvqibId7sj6eXah49SSqeGe8Ju98")
                .build();
        user = User.builder()
                .username("test")
                .password("$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha")
                .email("test@gmail.com")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("JUnit test for FileInfoRepository.findAll")
    void testFileInfoRepository() {
        fileInfoRepository.save(fileInfo);
        List<FileInfo> results = (List<FileInfo>) fileInfoRepository.findAll();
        assertFalse(results.isEmpty());
    }

    @Test
    @DisplayName("JUnit test for TokenRepository.findByToken")
    void testTokenRepository() {
        tokenRepository.save(token);
        List<Token> results = (List<Token>) tokenRepository.findAll();
        assertFalse(results.isEmpty());
    }

    @Test
    @DisplayName("JUnit test for UserRepository.findByUsername;")
    void testUserRepository() {
        userRepository.save(user);
        List<User> results = (List<User>) userRepository.findAll();
        assertFalse(results.isEmpty());
    }

}
