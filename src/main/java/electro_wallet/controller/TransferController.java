package electro_wallet.controller;

import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

        private final TransferService transferService;

        @PostMapping("/transactions")
        public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {

            return new ResponseEntity<>(transferService.transfer(request), HttpStatus.CREATED);
        }

}
