package electro_wallet.service;

import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.Mapper.TransferMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.enums.Currency;
import electro_wallet.enums.TransferStatus;
import electro_wallet.exception.AccessDeniedException;
import electro_wallet.exception.BadRequestException;
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
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransferService {

        private final TransferRepository transferRepository;
        private final TransferMapper transferMapper;
        private final AccountRepository accountRepository;
        private final UserRepository userRepository;


    public TransferResponse transfer(String email,  TransferRequest request) {

        Long senderId = userRepository.findByEmail(email)
                        .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        log.info("Начало перевода: отправитель ID {}, получатель (телефон) {}, сумма {}",
                senderId, request.receiver(), request.amount());

        Account senderAccount = accountRepository.findByUserId(senderId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));

        User receiver = userRepository.findByPhoneNumber(request.receiver())
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.RECEIVER_NOT_FOUND));

        Account receiverAccount = accountRepository.findByUserId(receiver.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));

        if (senderAccount.getUser().getId().equals(receiver.getId())) {
            log.warn("Попытка перевода самому себе заблокирована для ID: {}", senderId);
            throw new BadRequestException(ErrorMessages.SELF_TRANSFER_NOT_ALLOWED);
        }

        if (senderAccount.getBalance().compareTo(request.amount()) < 0) {
            log.warn("Недостаточно средств у отправителя ID: {}. Баланс: {}, Попытка перевода: {}",
                    senderId, senderAccount.getBalance(), request.amount());
            throw new BadRequestException(ErrorMessages.INSUFFICIENT_FUNDS);
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(request.amount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(request.amount()));

        accountRepository.save(receiverAccount);
        accountRepository.save(senderAccount);

        Transfer transfer = Transfer.builder()
                .sender(senderAccount)
                .receiver(receiverAccount)
                .message(request.message())
                .currency(Currency.KGS)
                .status(TransferStatus.SUCCESS)
                .amount(request.amount())
                .timestamp(LocalDateTime.now())
                .build();

        transferRepository.save(transfer);

        log.info("Перевод успешно завершен. Транзакция ID: {}, сумма: {}", transfer.getId(), request.amount());

        return transferMapper.toResponse(transfer);
    }

    @Transactional(readOnly = true)
    public Page<TransferResponse> getTransactionHistory(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));


        log.info("Запрос истории: страница {}, размер {}, для юзера ID: {}",
                pageable.getPageNumber(), pageable.getPageSize(), email);

        return transferRepository.findBySenderIdOrReceiverId(account.getId(), account.getId(), pageable)
                .map(transferMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public TransferResponse getTransactionById(String email, Long transferId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.TRANSFER_NOT_FOUND));

        boolean isSender = transfer.getSender() != null && transfer.getSender().getId().equals(user.getId());
        boolean isReceiver = transfer.getReceiver().getUser().equals(user.getId());

        if (!isSender && isReceiver) {
            log.warn("Пользователь {} пытался получить доступ к чужой транзакции ID: {}", email, transferId);
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }

        log.info("Транзакция ID {} успешно запрошена пользователем {}", transferId, email);
        return transferMapper.toResponse(transfer);
    }
}
