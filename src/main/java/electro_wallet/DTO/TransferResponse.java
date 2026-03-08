package electro_wallet.DTO;

import electro_wallet.enums.Currency;
import electro_wallet.enums.TransferStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(

        Long id,
        String receiver,
        BigDecimal amount,
        String message,
        Currency currency,
        TransferStatus status,
        LocalDateTime timestamp

) {
}
