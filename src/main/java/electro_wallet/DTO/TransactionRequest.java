package electro_wallet.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(

        @NotBlank(message = "Номер отправителя обязателен")
        @Pattern(regexp = "^\\+996\\d{9}$", message = "Некорректный формат номера отправителя")
        String senderNumber,

        @NotBlank(message = "Номер получателя обязателен")
        @Pattern(regexp = "^\\+996\\d{9}$", message = "Некорректный формат номера получателя")
        String receiverNumber,

        @NotNull(message = "Сумма перевода обязательна")
        @Positive(message = "Сумма перевода должна быть больше нуля")
        @DecimalMin(value = "5.00", message = "Минимальная сумма перевода — 5 сом")
        BigDecimal amount,

        @Size(max = 100, message = "Сообщение слишком длинное (максимум 100 символов)")
        String message

) {
}
