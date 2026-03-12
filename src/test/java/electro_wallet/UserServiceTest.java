package electro_wallet;

import electro_wallet.DTO.UserRequest;
import electro_wallet.Mapper.UserMapper;
import electro_wallet.entity.User;
import electro_wallet.exception.ConflictException;
import electro_wallet.repository.UserRepository;
import electro_wallet.service.AccountService;
import electro_wallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

            @Mock private UserRepository userRepository;
            @Spy private UserMapper userMapper;
            @Mock private AccountService accountService;
            @Mock private PasswordEncoder passwordEncoder;
            @InjectMocks private UserService userService;


    @Test
    void createUser_Success() {
        // Arrange
        UserRequest request = new UserRequest
                ("user", "test@mail.com", "pass123", "1234567");
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhoneNumber(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPass");

        // Act
        userService.createUser(request);

        // Assert
        verify(userRepository).save(any(User.class));
        verify(accountService).createAccount(any(User.class));
    }

    @Test
    void createUser_EmailExists_ThrowsException() {
        // Arrange
        UserRequest request = new UserRequest
                ("user", "test@mail.com", "pass123", "1234567");
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> userService.createUser(request));
    }
}
