package electro_wallet.service;


import electro_wallet.entity.Account;
import electro_wallet.entity.User;
import electro_wallet.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

        private final AccountRepository accountRepository;

    public Account createAccount(User user) {

        Account account = Account.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        return accountRepository.save(account);
    }
}
