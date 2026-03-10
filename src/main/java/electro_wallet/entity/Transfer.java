package electro_wallet.entity;

import electro_wallet.enums.Currency;
import electro_wallet.enums.TransferStatus;
import electro_wallet.enums.TransferType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transfers")
public class Transfer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sender_id", nullable = true)
        private Account sender;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "receiver_id")
        private Account receiver;

        @Column(nullable = false)
        private BigDecimal amount;

        private String message;

        @Enumerated(EnumType.STRING)
        private Currency currency = Currency.KGS;

        @Enumerated(EnumType.STRING)
        private TransferStatus status;

        @Enumerated(EnumType.STRING)
        private TransferType type;

        @CreationTimestamp
        private LocalDateTime timestamp;
}
