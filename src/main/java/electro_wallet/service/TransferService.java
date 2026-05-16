package electro_wallet.service;

import electro_wallet.DTO.DepositRequest;
import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import electro_wallet.entity.User;
import electro_wallet.enums.Status;
import electro_wallet.mapper.TransferMapper;
import electro_wallet.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

        private final TransferRepository transferRepository;
        private final TransferMapper transferMapper;
        private final UserService userService;

    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        User senderNumber = userService.findUserEntityByPhoneNumber(request.senderNumber());
        User receiverNumber = userService.findUserEntityByPhoneNumber(request.receiverNumber());

        Account senderAccount = senderNumber.getAccount();
        Account receiverAccount = receiverNumber.getAccount();

        Transfer transfer = new Transfer();
        transfer.setSenderNumber(senderAccount);
        transfer.setReceiverNumber(receiverAccount);
        transfer.setAmount(request.amount());
        transfer.setMessage(request.message());
        transfer.setTimestamp(LocalDateTime.now());
        transfer.setStatus(Status.PENDING);

        try {
            senderAccount.withdraw(request.amount());
            receiverAccount.deposit(request.amount());
            transfer.setStatus(Status.SUCCESS);

        } catch (Exception ex) {
            transfer.setStatus(Status.FAILED);
            throw ex;
        }

        transferRepository.save(transfer);

        return transferMapper.toResponse(transfer);
    }

    @Transactional
    public void deposit(DepositRequest request) {

        User user = userService.findUserEntityByPhoneNumber(request.phoneNumber());

        Account account = user.getAccount();
        account.deposit(request.amount());
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<TransferResponse> findAllBySenderNumber(String phoneNumber) {

        User user = userService.findUserEntityByPhoneNumber(phoneNumber);
        Account account = user.getAccount();

        List<Transfer> transfers = transferRepository.findAllBySenderNumber(account);

        return transfers.stream()
                .map(transferMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<TransferResponse> findAllByReceiverNumber(String phoneNumber) {

        User user = userService.findUserEntityByPhoneNumber(phoneNumber);
        Account account = user.getAccount();

        List<Transfer> transfers = transferRepository.findAllByReceiverNumber(account);

        return transfers.stream()
                .map(transferMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<TransferResponse> findAllUserTransfers(String phoneNumber) {

        User user = userService.findUserEntityByPhoneNumber(phoneNumber);
        Account account = user.getAccount();

        List<Transfer> transfers = transferRepository.findAllUserTransfers(account);

        return transfers.stream()
                .map(transferMapper::toResponse)
                .toList();
    }
}
