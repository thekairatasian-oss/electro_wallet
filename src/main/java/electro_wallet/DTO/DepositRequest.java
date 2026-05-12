package electro_wallet.DTO;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record DepositRequest(

        @NotBlank(message = "Номер телефона обязателен")
        @Pattern(regexp = "^996\\d{9}$", message = "Номер должен начинаться с 996")
        String phoneNumber,

        @NotNull(message = "Сумма перевода обязательна")
        @Positive(message = "Сумма должна быть больше нуля")
        @Digits(integer = 19, fraction = 2, message = "Некорректный формат суммы")
        BigDecimal amount

) {
}
