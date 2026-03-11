package electro_wallet.service;

import electro_wallet.DTO.AccountResponse;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.DTO.UserResponse;
import electro_wallet.Mapper.TransferMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.exception.ErrorMessages;
import electro_wallet.exception.ResourceNotFoundException;
import electro_wallet.repository.AccountRepository;
import electro_wallet.repository.TransferRepository;
import electro_wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

            private final UserRepository userRepository;
            private final TransferRepository transferRepository;
            private final AccountRepository accountRepository;
            private final TransferMapper transferMapper;

       @Transactional(readOnly = true)
       public UserResponse findUserByEmail(String email) {
           log.info("Попытка поиска пользователя по email: {}", email);

           User user = userRepository.findByEmail(email)
                   .orElseThrow(() -> {
                       log.warn("Пользователь с email {} не найден в базе данных", email);
                       return new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND);
                   });

           log.debug("Пользователь найден: ID={}", user.getId());

           Account account = accountRepository.findByUserId(user.getId())
                   .orElse(null);

           if (account == null) {
               log.warn("Внимание: у пользователя с ID={} не найден привязанный аккаунт", user.getId());
           }

           AccountResponse accountResponse = (account != null) ?
                   new AccountResponse(account.getBalance(), account.getCreatedAt()) : null;

           log.info("Успешно получен профиль пользователя: {}", user.getEmail());
           return new UserResponse(
                   user.getId(),
                   user.getUsername(),
                   user.getEmail(),
                   user.getPhoneNumber(),
                   accountResponse
           );
       }

       @Transactional(readOnly = true)
       public Page<TransferResponse> getHistoryForAdmin(Long userId, Pageable pageable) {

           log.info("Запрос истории транзакций для пользователя ID={}. Параметры пагинации: page={}, size={}",
                   userId, pageable.getPageNumber(), pageable.getPageSize());

           Account account = accountRepository.findByUserId(userId)
                   .orElseThrow(() -> {
                       log.warn("Попытка получения истории: аккаунт для пользователя ID={} не найден", userId);
                       return new ResourceNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND);
                   });

           Page<Transfer> transfers = transferRepository.findBySenderIdOrReceiverId(account.getId(), account.getId(),
                   pageable);

           log.info("История транзакций успешно получена: найдено {} записей", transfers.getTotalElements());
           return transfers.map(transferMapper::toResponse);
       }

       @Transactional(readOnly = true)
       public TransferResponse getTransactionById(Long transferId) {
           log.info("Запрос детальной информации по транзакции с ID={}", transferId);

           Transfer transfer = transferRepository.findById(transferId)
                   .orElseThrow(() -> {
                       log.warn("Транзакция с ID={} не существует", transferId);
                       return new ResourceNotFoundException(ErrorMessages.TRANSFER_NOT_FOUND);
                   });

           log.debug("Детали транзакции успешно считаны для ID={}", transferId);
           return transferMapper.toResponse(transfer);
       }

       public void blockUser(Long userId) {
           log.info("Администратор инициировал блокировку пользователя с ID={}", userId);

           User user = userRepository.findById(userId)
                   .orElseThrow(() -> {
                       log.error("Ошибка блокировки: пользователь с ID={} не найден", userId);
                       return new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND);
                   });

           user.setEnabled(false);
           userRepository.save(user);
           log.info("Пользователь с ID={} успешно заблокирован", userId);
       }

       public void unblockUser(Long userId) {
           log.info("Администратор инициировал разблокировку пользователя с ID={}", userId);

           User user = userRepository.findById(userId)
                   .orElseThrow(() -> {
                       log.error("Ошибка разблокировки: пользователь с ID={} не найден", userId);
                       return new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND);
                   });

           user.setEnabled(true);
           userRepository.save(user);
           log.info("Пользователь с ID={} успешно разблокирован", userId);
       }
}
