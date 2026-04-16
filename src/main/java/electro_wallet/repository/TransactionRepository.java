package electro_wallet.repository;

import electro_wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.senderNumber.accountNumber = :phone OR" +
            " t.receiverNumber.accountNumber = :phone ORDER BY t.timestamp DESC")
    List<Transaction> findAllByPhone(@Param("phone") String phone);

    @Query("SELECT t FROM Transaction t WHERE t.senderNumber.accountNumber = :phone ORDER BY t.timestamp DESC")
    List<Transaction> findOutcoming(@Param("phone") String phone);

    @Query("SELECT t FROM Transaction t WHERE t.receiverNumber.accountNumber = :phone ORDER BY t.timestamp DESC")
    List<Transaction> findIncoming(@Param("phone") String phone);

}


