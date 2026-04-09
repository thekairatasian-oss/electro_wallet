package electro_wallet.mapper;

import electro_wallet.DTO.TransactionRequest;
import electro_wallet.DTO.TransactionResponse;
import electro_wallet.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    Transaction toEntity(TransactionRequest request);

    TransactionResponse toResponse(Transaction transaction);
}
