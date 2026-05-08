package electro_wallet.entity;

import electro_wallet.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_number_id")
    private Account senderNumber;

    @ManyToOne
    @JoinColumn(name = "receiver_number_id")
    private Account receiverNumber;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
