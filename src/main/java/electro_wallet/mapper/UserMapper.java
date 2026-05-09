package electro_wallet.mapper;

import electro_wallet.DTO.UserRequest;
import electro_wallet.DTO.UserResponse;
import electro_wallet.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface UserMapper {

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest request);

    UserResponse toResponse(User user);

}
