package electro_wallet.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(

        BigDecimal balance,
        LocalDateTime createdAt
) {
}
