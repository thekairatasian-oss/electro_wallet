package electro_wallet.repository;

import electro_wallet.entity.Account;
import electro_wallet.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository <Transfer, Long>{

    List<Transfer> findAllBySender(Account sender);

    List<Transfer> findAllByReceiver(Account receiver);

    @Query("SELECT t FROM Transfer t WHERE t.senderNumber = :account OR" +
            " t.receiverNumber = :account ORDER BY t.timestamp DESC")
    List<Transfer> findAllUserTransfers(@Param("account") Account account);
}
