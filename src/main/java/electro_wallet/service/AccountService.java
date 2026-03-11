package electro_wallet.service;


import electro_wallet.DTO.AccountResponse;
import electro_wallet.DTO.DepositRequest;
import electro_wallet.Mapper.AccountMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.enums.Currency;
import electro_wallet.enums.TransferStatus;
import electro_wallet.enums.TransferType;
import electro_wallet.exception.ErrorMessages;
import electro_wallet.exception.ResourceNotFoundException;
import electro_wallet.repository.AccountRepository;
import electro_wallet.repository.TransferRepository;
import electro_wallet.repository.UserRepository;
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
        private final UserRepository userRepository;
        private final TransferRepository transferRepository;
        private final AccountMapper accountMapper;

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

    public AccountResponse deposit(String email, DepositRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));

        account.setBalance(account.getBalance().add(request.amount()));
        accountRepository.save(account);

        Transfer depositRecord = Transfer.builder()
                .receiver(account)
                .sender(null)
                .amount(request.amount())
                .type(TransferType.DEPOSIT)
                .status(TransferStatus.SUCCESS)
                .currency(Currency.KGS)
                .timestamp(LocalDateTime.now())
                .message("Пополнение счета")
                .build();

        transferRepository.save(depositRecord);

        log.info("Баланс пользователя {} успешно пополнен на {} KGS", email, request.amount());
        return accountMapper.toResponse(account);
    }
}
