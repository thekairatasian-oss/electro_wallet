package electro_wallet.service;


import electro_wallet.entity.Account;
import electro_wallet.entity.User;
import electro_wallet.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

        private final AccountRepository accountRepository;

    public Account createAccount(User user) {

        log.debug("Запрос на создание аккаунта для пользователя ID: {}", user.getId());

        Account account = Account.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Аккаунт успешно создан. ID аккаунта: {}, для пользователя ID: {}",
                account.getId(), user.getId());

        return accountRepository.save(account);
    }
}
