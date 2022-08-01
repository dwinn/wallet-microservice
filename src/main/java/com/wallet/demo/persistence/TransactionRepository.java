package com.wallet.demo.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {

    Optional<TransactionEntity> findByTransactionId(String transactionId);

    List<TransactionEntity> findByAccountId(int accountId);
}
