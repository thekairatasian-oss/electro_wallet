package electro_wallet.controller;

import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

        private final TransferService transferService;

        @PostMapping("/transactions")
        public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request, Principal principal)
        {
            return new ResponseEntity<>(transferService.transfer(principal.getName(), request), HttpStatus.CREATED);
        }

        @GetMapping("/history")
        public ResponseEntity<Page<TransferResponse>> getAllTransfers(@PageableDefault(size = 10, sort = "timestamp",
        direction = Sort.Direction.DESC) Pageable pageable, Principal principal) {
                return ResponseEntity.ok(transferService.getTransactionHistory(principal.getName(), pageable));
        }

        @GetMapping("/{transferId}")
        public ResponseEntity<TransferResponse> getTransferById(@PathVariable Long transferId, Principal principal) {
                return ResponseEntity.ok(transferService.getTransactionById(principal.getName(), transferId));
        }
}
