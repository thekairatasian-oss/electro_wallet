package electro_wallet.service;

import electro_wallet.DTO.UserRequest;
import electro_wallet.DTO.UserResponse;
import electro_wallet.Mapper.UserMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.User;
import electro_wallet.enums.Role;
import electro_wallet.exception.ConflictException;
import electro_wallet.exception.ErrorMessages;
import electro_wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final AccountService accountService;
        private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {

        log.info("Попытка регистрации пользователя с email: {}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            log.warn("Регистрация отклонена: email {} уже занят", request.email());
            throw new ConflictException(ErrorMessages.USER_ALREADY_EXISTS);
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            log.warn("Регистрация отклонена: email {} уже занят", request.email());
            throw new ConflictException(ErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
        }

       User user = User.builder()
               .username(request.username())
               .email(request.email())
               .password(request.password() != null ? passwordEncoder.encode(request.password()) : null)
               .phoneNumber(request.phoneNumber())
               .enabled(true)
               .role(Role.USER)
               .build();

        userRepository.save(user);
        log.debug("Пользователь сохранен в БД, ID: {}", user.getId());

        Account account = accountService.createAccount(user);
        user.setAccount(account);

        log.info("Пользователь успешно зарегистрирован. ID: {}, Email: {}", user.getId(), user.getEmail());
        return userMapper.toResponse(user);
    }
}
