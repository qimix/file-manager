package ru.netology.file_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignInFrontendRequest {
        @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
        @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
        @NotBlank(message = "Адрес электронной почты не может быть пустыми")
        @Email(message = "Email адрес должен быть в формате user@example.com")
        private String login;

        @Schema(description = "Пароль", example = "my_1secret1_password")
        @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
        private String password;

        public String getEmail() {
            return login;
        }

        public String getPassword() {
            return password;
        }
}
