package electro_wallet.DTO;

public record UserResponse(

        Long id,
        String username,
        String email,
        String phoneNumber,
        AccountResponse account

) {
}
