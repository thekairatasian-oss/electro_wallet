package electro_wallet.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Имя пользователя обязателен для заполнения")
        @Size(min = 2, max = 20, message = "Имя пользователя должен быть от 2 до 20 символов")
        String username,

        @NotBlank(message = "Email обязателен для заполнения")
        @Email(message = "Некорректный Email")
        String email,

        @NotBlank(message = "Пароль обязателен для заполнения")
        @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
        String password,

        @NotBlank(message = "Номер телефона обязателен для заполнения")
        @Pattern(regexp = "^\\+996\\d{9}$", message = "Номер телефона должен начинаться в формате +996")
        String phoneNumber

) {
}
