package electro_wallet.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(

        Long id,
        String senderNumber,
        String receiverNumber,
        BigDecimal amount,
        String message,
        LocalDateTime timestamp

) {
}
