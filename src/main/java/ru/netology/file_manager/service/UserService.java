package ru.netology.file_manager.service;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.netology.file_manager.model.Role;
import ru.netology.file_manager.repository.UserRepository;
import ru.netology.file_manager.model.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtService.class);

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        logger.info("start save: {}", user.getUsername());
        return userRepository.save(user);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        logger.info("start create: {}", user.getUsername());
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.error("error user {} exists: ", user.getUsername());
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("error user {} exists: ", user.getUsername());
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        logger.info("start getByUsername: {} ", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * Получение пользователя по email
     *
     * @return пользователь
     */
    public User getByEmail(String email) {
        logger.info("start getByEmail: {} ", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        logger.info("start userDetailsService");
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        logger.info("start getCurrentUser");
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("get user: {}", username);
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        logger.info("start getAdmin");
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
