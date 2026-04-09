package electro_wallet.DTO;

import electro_wallet.enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(

        Long id,
        String sender,
        String receiver,
        BigDecimal amount,
        String message,
        LocalDate timestamp,
        TransactionStatus status

) {
}
