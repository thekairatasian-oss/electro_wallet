package electro_wallet;

import electro_wallet.DTO.AccountResponse;
import electro_wallet.DTO.DepositRequest;
import electro_wallet.Mapper.AccountMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.repository.AccountRepository;
import electro_wallet.repository.TransferRepository;
import electro_wallet.repository.UserRepository;
import electro_wallet.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

       @Spy private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
       @Mock private TransferRepository transferRepository;
       @Mock private UserRepository userRepository;
       @Mock private AccountRepository accountRepository;
       @InjectMocks private AccountService accountService;

    @Test
    @DisplayName("Успешное создание аккаунта")
    @MockitoSettings(strictness = Strictness.LENIENT)
    void createAccount_Success() {

        User user = new User();
        user.setId(1L);
        user.setEmail("user@test.com");
        user.setUsername("testuser");

        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account accountEntity = accountService.createAccount(user);

        assertNotNull(accountEntity);
        assertEquals(BigDecimal.ZERO, accountEntity.getBalance());

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("Успешное пополнение баланса")
    void deposit_Success() {

        String email = "kairat@mail.ru";
        BigDecimal amount = new BigDecimal("100.00");
        DepositRequest request = new DepositRequest(amount);

        User user = new User();
        Account account = new Account();
        account.setBalance(new BigDecimal("500.00"));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.of(account));

        AccountResponse response = accountService.deposit(email, request);

        assertEquals(new BigDecimal("600.00"), response.balance());

        verify(accountRepository).save(any(Account.class));
        verify(transferRepository).save(any(Transfer.class));
    }
}
