package electro_wallet.controller;

import electro_wallet.DTO.TransferResponse;
import electro_wallet.DTO.UserResponse;
import electro_wallet.service.AdminService;
import electro_wallet.service.TransferService;
import electro_wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

            private final UserService userService;
            private final TransferService transferService;
            private final AdminService adminService;

       @GetMapping("/users/search")
       public ResponseEntity<UserResponse> findByUserEmail(@RequestParam String email){
           return ResponseEntity.ok(adminService.findUserByEmail(email));
       }

       @GetMapping("/users/{userId}")
       public ResponseEntity<Page<TransferResponse>> getAllTransactions(@PathVariable Long userId, Pageable pageable){
           return ResponseEntity.ok(adminService.getHistoryForAdmin(userId, pageable));
       }

       @GetMapping("/transactions/{transferId}")
       public ResponseEntity<TransferResponse> getTransactionById(@PathVariable Long transferId){
           return ResponseEntity.ok(adminService.getTransactionById(transferId));
       }

       @PatchMapping("/users/{userId}/block")
       public ResponseEntity<Void> blockUser(@PathVariable Long userId){
           adminService.blockUser(userId);
           return ResponseEntity.ok().build();
       }

       @PatchMapping("/users/{userId}/unblock")
       public ResponseEntity<Void> unblockUser(@PathVariable Long userId){
           adminService.unblockUser(userId);
           return ResponseEntity.ok().build();
       }
}
