package electro_wallet.DTO;

import electro_wallet.enums.Status;
import java.math.BigDecimal;

public record TransferResponse(

        Long id,
        String senderNumber,
        String receiverNumber,
        BigDecimal amount,
        String message,
        Status status

) {
}
