package electro_wallet.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Имя пользователя обязательно")
        @Size(min = 2, max = 20, message = "Имя должно быть от 2 до 20 символов")
        String username,

        @NotBlank(message = "Email обязателен")
        @Email(message = "Введите корректный адрес электронной почты")
        String email,

        @NotBlank(message = "Пароль обязателен")
        @Size(min = 8, max = 50, message = "Пароль должен быть от 8 до 50 символов")
        String password,

        @NotBlank(message = "Номер телефона обязателен")
        @Pattern(regexp = "^996\\d{9}$", message = "Номер должен начинаться с 996")
        String phoneNumber

) {
}
