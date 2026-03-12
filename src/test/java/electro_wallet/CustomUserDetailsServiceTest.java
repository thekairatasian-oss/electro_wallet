package electro_wallet;

import electro_wallet.entity.User;
import electro_wallet.enums.Role;
import electro_wallet.repository.UserRepository;
import electro_wallet.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

        @Mock private UserRepository userRepository;
        @InjectMocks private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_UserExists() {

        String email = "kairat@mail.com";
        User user = User.builder()
                .email(email)
                .password("450568kairat")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }
}
