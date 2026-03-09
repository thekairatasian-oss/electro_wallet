package electro_wallet.repository;

import electro_wallet.entity.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

        Page<Transfer> findBySenderIdOrReceiverId(Long senderId, Long receiverId, Pageable pageable);

        Page<Transfer> findAll(Pageable pageable);

}
