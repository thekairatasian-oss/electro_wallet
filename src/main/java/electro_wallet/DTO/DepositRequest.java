package electro_wallet.DTO;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record DepositRequest(

        @Positive(message = "Сумма должна быть больше нуля")
        BigDecimal amount

) {
}
