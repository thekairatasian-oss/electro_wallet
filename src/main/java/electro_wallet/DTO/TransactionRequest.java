package electro_wallet.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record TransactionRequest(

        @NotBlank(message = "Номер телефона отправителя обязателен")
        @Pattern(regexp = "^\\+996\\d{9}$", message = "Некорректный формат номера отправителя")
        String sender,

        @NotBlank(message = "Номер телефона получателя обязателен")
        @Pattern(regexp = "^\\+996\\d{9}$", message = "Некорректный формат номера получателя")
        String receiver,

        @NotNull(message = "Сумма обязательна")
        @DecimalMin(value = "10.00", message = "Минимальная сумма перевода — 10.00")
        BigDecimal amount,

        String message

) {
}
