package electro_wallet;

import electro_wallet.DTO.TransferRequest;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.exception.BadRequestException;
import electro_wallet.repository.AccountRepository;
import electro_wallet.repository.TransferRepository;
import electro_wallet.repository.UserRepository;
import electro_wallet.service.TransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

            @Mock private TransferRepository transferRepository;
            @Mock private AccountRepository accountRepository;
            @Mock private UserRepository userRepository;
            @InjectMocks private TransferService transferService;

      @Test
      @DisplayName("Успешная транзакция")
      void transfer_Success() {

          String senderEmail = "sender@mail.com";
          String receiverPhone = "996555000000";
          BigDecimal amount = new BigDecimal("100.00");

          User sender = User.builder().id(1L).build();
          User receiver = User.builder().id(2L).build();

          Account senderAccount = Account.builder().user(sender).balance(new BigDecimal("500.00")).build();
          Account receiverAccount = Account.builder().user(receiver).balance(new BigDecimal("500.00")).build();

          when(userRepository.findByEmail(senderEmail)).thenReturn(Optional.of(sender));
          when(userRepository.findByPhoneNumber(receiverPhone)).thenReturn(Optional.of(receiver));
          when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(senderAccount));
          when(accountRepository.findByUserId(2L)).thenReturn(Optional.of(receiverAccount));

          TransferRequest request = new TransferRequest(receiverPhone, amount, "Подарок");
          transferService.transfer(senderEmail, request);

          assertEquals(new BigDecimal("400.00"), senderAccount.getBalance());
          assertEquals(new BigDecimal("600.00"), receiverAccount.getBalance());

          verify(accountRepository).save(senderAccount);
          verify(accountRepository).save(receiverAccount);
          verify(transferRepository).save(any(Transfer.class));
      }

    @Test
    @DisplayName("Перевод невозможен: недостаточно средств")
    void transfer_InsufficientFunds_ThrowsException() {

        String email = "sender@test.com";
        BigDecimal balance = new BigDecimal("50.00");
        BigDecimal amountToTransfer = new BigDecimal("100.00");

        User sender = User.builder().id(1L).build();
        Account senderAccount = Account.builder()
                .user(sender)
                .balance(balance)
                .build();


        when(userRepository.findByEmail(email)).thenReturn(Optional.of(sender));
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(senderAccount));

        when(userRepository.findByPhoneNumber(anyString()))
                .thenReturn(Optional.of(User.builder().id(2L).build()));
        when(accountRepository.findByUserId(2L))
                .thenReturn(Optional.of(Account.builder().user(User.builder().id(2L).build()).build()));

        TransferRequest request = new TransferRequest("996555000000", amountToTransfer, "Подарок");

        assertThrows(BadRequestException.class, () -> transferService.transfer(email, request));
    }

    @Test
    void getTransactionHistory_Success() {

        String email = "user@test.com";
        User user = User.builder().id(1L).build();
        Account account = Account.builder().id(100L).build();
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(accountRepository.findByUserId(1L)).thenReturn(Optional.of(account));

        when(transferRepository.findBySenderIdOrReceiverId(100L, 100L, pageable))
                .thenReturn(Page.empty());

        transferService.getTransactionHistory(email, pageable);

        verify(transferRepository).findBySenderIdOrReceiverId(100L, 100L, pageable);
    }
}
