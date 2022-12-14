package com.wallet.demo.service;

import com.wallet.demo.exception.AccountNotFoundException;
import com.wallet.demo.exception.DuplicateAccountException;
import com.wallet.demo.models.Account;
import com.wallet.demo.persistence.AccountEntity;
import com.wallet.demo.persistence.AccountRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Handle API calls from the Wallet controller.
 *
 * @author David Winn
 */
@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private static final double DEFAULT_ACCOUNT_BALANCE = 0;

    private final AccountRepository accountRepository;
    private final Mapper mapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, Mapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    /**
     * Create an account, if the account name is unique.
     *
     * @param account The account object that we wish to create.
     */
    @Transactional
    public void createAccount(Account account) {
        accountRepository.findByName(account.getName())
                .ifPresentOrElse(result -> {
                    throw new DuplicateAccountException(String.format("An account with the name [%s] is already present.", result.getName()));
                }, () -> {

                    this.save(
                            new AccountEntity(account.getId(), account.getName(), DEFAULT_ACCOUNT_BALANCE)
                    );

                    LOGGER.info("Account successfully created with the name [{}].", account.getName());
                });
    }

    /**
     * Get the information belonging to an account, such as their balance.
     *
     * @param accountId The ID of the account that we wish to get information for.
     *
     * @return An {@link Account} object containing account information.
     */
    @Transactional(readOnly = true)
    public Account getAccount(int accountId) {
        return accountRepository.findById(accountId)
                .map(entity -> mapper.map(entity, Account.class))
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account not found with account ID [%d].", accountId)));
    }

    /**
     * Save a new account to the database.
     *
     * @param accountEntity The account object to save to the database.
     */
    public void save(AccountEntity accountEntity) {
        accountRepository.save(accountEntity);

        LOGGER.info("Saving account information. [{}]", accountEntity);
    }
}
