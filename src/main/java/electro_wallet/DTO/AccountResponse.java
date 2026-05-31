package electro_wallet.DTO;

import electro_wallet.enums.Currency;
import java.math.BigDecimal;

public record AccountResponse(

        Long id,
        String accountNumber,
        BigDecimal balance,
        Currency currency

) {
}
