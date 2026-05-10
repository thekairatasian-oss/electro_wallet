package electro_wallet.entity;

import electro_wallet.enums.Currency;
import electro_wallet.exception.ApiException;
import electro_wallet.exception.ErrorMessages;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Version
    private Long version;

    @OneToOne
    private User user;

    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException(ErrorMessages.BadRequest.INVALID_AMOUNT, HttpStatus.BAD_REQUEST);
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException(ErrorMessages.BadRequest.INVALID_AMOUNT, HttpStatus.BAD_REQUEST);
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new ApiException(ErrorMessages.BadRequest.INSUFFICIENT_FUNDS, HttpStatus.BAD_REQUEST);
        }
        this.balance = this.balance.subtract(amount);
    }
}
