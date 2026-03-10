package electro_wallet.controller;

import electro_wallet.DTO.AccountResponse;
import electro_wallet.DTO.DepositRequest;
import electro_wallet.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

        private final AccountService accountService;

    @PutMapping("/deposit")
    public ResponseEntity<AccountResponse> deposit(@Valid @RequestBody DepositRequest request, Principal principal) {
        return ResponseEntity.ok(accountService.deposit(principal.getName(), request));
    }
}
