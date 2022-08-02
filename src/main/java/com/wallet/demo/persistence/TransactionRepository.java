package com.wallet.demo.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data access for the {@link TransactionRepository}, using {@link CrudRepository}.
 *
 * @author David Winn
 */
@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {

    Optional<TransactionEntity> findByTransactionId(String transactionId);

    List<TransactionEntity> findByAccountId(int accountId);
}
