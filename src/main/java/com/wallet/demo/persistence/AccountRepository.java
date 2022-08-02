package com.wallet.demo.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Data access for the {@link AccountRepository}, using {@link CrudRepository}.
 *
 * @author David Winn
 */
@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {

    Optional<AccountEntity> findById(int id);

    Optional<AccountEntity> findByName(String name);
}
