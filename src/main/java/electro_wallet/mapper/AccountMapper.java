package electro_wallet.mapper;

import electro_wallet.DTO.AccountResponse;
import electro_wallet.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toResponse(Account account);

}
