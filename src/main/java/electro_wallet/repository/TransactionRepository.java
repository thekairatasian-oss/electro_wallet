package electro_wallet.repository;

import electro_wallet.entity.Account;
import electro_wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderOrReceiver(Account sender, Account receiver);

    List<Transaction> findBySender(Account sender);

    List<Transaction> findByReceiver(Account receiver);

    List<Transaction> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
