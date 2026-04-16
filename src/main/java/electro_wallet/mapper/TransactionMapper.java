package electro_wallet.mapper;

import electro_wallet.DTO.TransactionRequest;
import electro_wallet.DTO.TransactionResponse;
import electro_wallet.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "senderNumber", ignore = true)
    @Mapping(target = "receiverNumber", ignore = true)
    Transaction toEntity(TransactionRequest request);

    @Mapping(source = "senderNumber.accountNumber", target = "senderNumber")
    @Mapping(source = "receiverNumber.accountNumber", target = "receiverNumber")
    TransactionResponse toResponse(Transaction transaction);
}
