package electro_wallet.controller;

import electro_wallet.DTO.UserRequest;
import electro_wallet.DTO.UserResponse;
import electro_wallet.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/username")
    public ResponseEntity<UserResponse> getUserByUsername(String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/phone")
    public ResponseEntity<UserResponse> getUserByPhoneNumber(String phoneNumber) {
        return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
    }

    @PutMapping("/block")
    public ResponseEntity<UserResponse> blockUser(String email) {
        return ResponseEntity.ok(userService.blockUser(email));
    }

    @PutMapping("/unblock")
    public ResponseEntity<UserResponse> unblockUser(String email) {
        return ResponseEntity.ok(userService.unblockUser(email));
    }
}
