package electro_wallet.service;

import electro_wallet.DTO.UserRequest;
import electro_wallet.DTO.UserResponse;
import electro_wallet.Mapper.UserMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.User;
import electro_wallet.enums.Role;
import electro_wallet.exception.ConflictException;
import electro_wallet.exception.ErrorMessages;
import electro_wallet.repository.AccountRepository;
import electro_wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final AccountService accountService;

    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException(ErrorMessages.USER_ALREADY_EXISTS);
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new ConflictException(ErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
        }

       User user = User.builder()
               .username(request.username())
               .email(request.email())
               .password(request.password())
               .phoneNumber(request.phoneNumber())
               .enabled(true)
               .role(Role.USER)
               .build();

        userRepository.save(user);

        Account account = accountService.createAccount(user);
        user.setAccount(account);

        return userMapper.toResponse(user);
    }
}
