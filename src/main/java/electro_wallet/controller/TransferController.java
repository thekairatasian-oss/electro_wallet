package electro_wallet.controller;

import electro_wallet.DTO.DepositRequest;
import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/create")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(transferService.transfer(request));
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody DepositRequest request) {
        transferService.deposit(request);
        return ResponseEntity.ok("Баланс успешно пополнен");
    }

    @GetMapping("/sender/{phoneNumber}")
    public ResponseEntity<List<TransferResponse>> findAllBySenderHistory(@PathVariable String phoneNumber) {
        List<TransferResponse> transfers = transferService.findAllBySenderNumber(phoneNumber);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/receiver/{phoneNumber}")
    public ResponseEntity<List<TransferResponse>> findAllByReceiverHistory(@PathVariable String phoneNumber) {
        List<TransferResponse> transfers = transferService.findAllByReceiverNumber(phoneNumber);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/user/{phoneNumber}")
    public ResponseEntity<List<TransferResponse>> findAllUserTransfers(@PathVariable String phoneNumber) {
        List<TransferResponse> transfers = transferService.findAllUserTransfers(phoneNumber);
        return ResponseEntity.ok(transfers);
    }
}
