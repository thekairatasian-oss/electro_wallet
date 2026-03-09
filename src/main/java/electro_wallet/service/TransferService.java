package electro_wallet.service;

import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.Mapper.TransferMapper;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.enums.Currency;
import electro_wallet.enums.TransferStatus;
import electro_wallet.exception.BadRequestException;
import electro_wallet.exception.ErrorMessages;
import electro_wallet.exception.ResourceNotFoundException;
import electro_wallet.repository.AccountRepository;
import electro_wallet.repository.TransferRepository;
import electro_wallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
public class TransferService {

        private final TransferRepository transferRepository;
        private final TransferMapper transferMapper;
        private final AccountRepository accountRepository;
        private final UserRepository userRepository;
        private final UserService userService;


    public TransferResponse transfer(TransferRequest request) {

        Account senderAccount = accountRepository.findByUserId(request.sender())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));

        User receiver = userRepository.findByPhoneNumber(request.receiver())
                .orElseThrow(()-> new ResourceNotFoundException(ErrorMessages.RECEIVER_NOT_FOUND));

        Account receiverAccount = accountRepository.findByUserId(receiver.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND));

        if (senderAccount.getUser().getId().equals(receiver.getId())) {
            throw new BadRequestException(ErrorMessages.SELF_TRANSFER_NOT_ALLOWED);
        }

        if (senderAccount.getBalance().compareTo(request.amount()) < 0) {
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

        return transferMapper.toResponse(transfer);
    }
}
