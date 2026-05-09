package electro_wallet.mapper;

import electro_wallet.DTO.TransferRequest;
import electro_wallet.DTO.TransferResponse;
import electro_wallet.entity.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "senderNumber", ignore = true)
    @Mapping(target = "receiverNumber", ignore = true)
    Transfer toEntity(TransferRequest request);

    @Mapping(target = "senderNumber.phoneNumber", source = "senderNumber")
    @Mapping(target = "receiverNumber.phoneNumber", source = "receiverNumber")
    TransferResponse toResponse(Transfer transfer);
}
