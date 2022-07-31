package com.wallet.demo.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {

    Optional<TransactionEntity>  findById(UUID id);
}
