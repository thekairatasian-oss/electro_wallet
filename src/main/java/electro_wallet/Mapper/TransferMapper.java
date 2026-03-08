package electro_wallet.Mapper;

import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.entity.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {

        @Mapping(target = "receiver", source = "receiver.user.phoneNumber")
        TransferResponse toResponse(Transfer transfer);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "sender", ignore = true)
        @Mapping(target = "receiver", ignore = true)
        @Mapping(target = "timestamp", ignore = true)
        @Mapping(target = "status", ignore = true)
        Transfer toEntity(TransferRequest transferRequest);

}
