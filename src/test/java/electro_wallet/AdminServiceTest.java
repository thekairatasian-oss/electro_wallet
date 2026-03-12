package electro_wallet;

import electro_wallet.DTO.TransferResponse;
import electro_wallet.DTO.UserResponse;
import electro_wallet.Mapper.TransferMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.exception.ResourceNotFoundException;
import electro_wallet.repository.AccountRepository;
import electro_wallet.repository.TransferRepository;
import electro_wallet.repository.UserRepository;
import electro_wallet.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

        @Mock private TransferRepository transferRepository;
        @Mock private UserRepository userRepository;
        @Mock private AccountRepository accountRepository;
        @Spy private TransferMapper transferMapper = Mappers.getMapper(TransferMapper.class);
        @InjectMocks private AdminService adminService;

        @Test
        @DisplayName("Успешная блокировка пользователя")
        void blockUser_Success() {

            Long userId = 1L;
            User user = new User();
            user.setId(userId);
            user.setEnabled(true);

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            adminService.blockUser(userId);
            assertFalse(user.isEnabled(), "Пользователь должен быть заблокирован (enabled = false)");
            verify(userRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Ошибка при блокировке: пользователь не найден")
        void blockUserNotFound() {

            Long userId = 1L;
            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                adminService.blockUser(userId);
            }, "Должно быть выброшено исключение, если юзер не найден");

            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        @DisplayName("Успешный поиск пользователя по Email с данными аккаунта")
        void findUserByEmail_Success() {

            String email = "kairat@mail.ru";
            User user = new User();
            user.setId(1L);
            user.setEmail(email);
            user.setUsername("Kairat");

            Account account = new Account();
            account.setBalance(new BigDecimal(500));
            account.setCreatedAt(LocalDateTime.now());

            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
            when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.of(account));

            UserResponse response = adminService.findUserByEmail(email);

            assertNotNull(response);
            assertEquals(email, response.email());
            assertNotNull(response.account());
            assertEquals(new BigDecimal(500), response.account().balance());
        }

        @Test
        @DisplayName("Успешная разблокировка пользователя")
        void unblockUser_Success() {

            Long userId = 1L;
            User user = new User();
            user.setId(userId);
            user.setEnabled(false);

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            adminService.unblockUser(userId);
            assertTrue(user.isEnabled());
            verify(userRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Успешное получение истории транзакций админом")
        void getHistoryForAdmin_Success() {

            Long userId = 1L;
            Long accountId = 2L;
            Pageable pageable = PageRequest.of(0, 10);

            Account account = new Account();
            account.setId(accountId);

            Transfer transfer = new Transfer();
            transfer.setId(100L);
            transfer.setAmount(new BigDecimal(1000));

            // Создаем "страницу" с одной транзакцией
            Page<Transfer> transferPage = new PageImpl<>(List.of(transfer));

            when(accountRepository.findByUserId(userId)).thenReturn(Optional.of(account));
            when(transferRepository.findBySenderIdOrReceiverId(accountId, accountId, pageable))
                    .thenReturn(transferPage);

            // Act
            Page<TransferResponse> result = adminService.getHistoryForAdmin(userId, pageable);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            verify(transferRepository).findBySenderIdOrReceiverId(accountId, accountId, pageable);
        }

    @Test
    @DisplayName("Успешное получение транзакции по ID")
    void getTransactionById_Success() {
        // Arrange
        Long transferId = 100L;
        Transfer transfer = new Transfer();
        transfer.setId(transferId);
        transfer.setAmount(new BigDecimal("250.00"));

        when(transferRepository.findById(transferId)).thenReturn(Optional.of(transfer));

        // Act
        TransferResponse response = adminService.getTransactionById(transferId);

        // Assert
        assertNotNull(response);
        verify(transferRepository).findById(transferId);
    }


}
