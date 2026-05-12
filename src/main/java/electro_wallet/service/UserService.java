package electro_wallet.service;

import electro_wallet.DTO.UserRequest;
import electro_wallet.DTO.UserResponse;
import electro_wallet.entity.Account;
import electro_wallet.entity.User;
import electro_wallet.enums.Currency;
import electro_wallet.enums.Role;
import electro_wallet.exception.ApiException;
import electro_wallet.exception.ErrorMessages;
import electro_wallet.mapper.UserMapper;
import electro_wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ApiException(ErrorMessages.BadRequest.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber())){
            throw new ApiException(ErrorMessages.BadRequest.PHONE_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.toEntity(request);

        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(Currency.KGS);
        account.setUser(user);

        user.setAccount(account);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setActive(true);

        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        return userMapper.toResponse(userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorMessages.NotFound.USER_NOT_FOUND, HttpStatus.NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        return userMapper.toResponse(userRepository.findByPhoneNumber(phoneNumber)
        .orElseThrow(() -> new ApiException(ErrorMessages.NotFound.PHONE_NUMBER_NOT_FOUND, HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public UserResponse blockUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorMessages.NotFound.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setActive(false);
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse unblockUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorMessages.NotFound.EMAIL_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setActive(true);
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    User findUserEntityByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ApiException(ErrorMessages.NotFound.PHONE_NUMBER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
