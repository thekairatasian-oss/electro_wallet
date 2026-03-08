package electro_wallet.DTO;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record TransferRequest(

        @NotNull(message = "ID отправителя обязательно")
        Long sender,

        @NotBlank(message = "Номер телефон получателя обязательна")
        @Pattern(regexp = "^\\+996\\d{9}$",
                message = "Номер получателя должен начинаться с +996 и содержать 9 цифр после кода"
        )
        String receiver,

        @NotNull(message = "Сумма перевода обязательна")
        @Positive(message = "Сумма перевода должна быть больше нуля")
        BigDecimal amount,

        @Size(max = 100, message = "Сообщение не должно превышать 100 символов")
        String message

) {
}
