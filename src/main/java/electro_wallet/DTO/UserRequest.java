package electro_wallet.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Имя должно быть заполнено")
        @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
        String username,

        @NotBlank(message = "email обязателен для заполнения")
        @Email(message = "Некорректный формат email")
        String email,

        @NotBlank(message = "Пароль обязателен для заполнения")
        @Size(min = 8, max = 100, message = "Пароль должен быть от 8 до 100 символов")
        String password,

        @NotBlank(message = "Номер телефона обязателен")
        @Pattern(regexp = "^\\+996\\d{9}$", message =
                "Номер должен начинаться с +996 и содержать 9 цифр после кода (всего 13 символов)")
        String phoneNumber
) {
}
