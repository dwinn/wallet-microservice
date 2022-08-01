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

    @Transactional
    public void createAccount(Account account) {
        accountRepository.findByName(account.getName())
                .ifPresentOrElse(result -> {
                    throw new DuplicateAccountException(String.format("An account with the name [%s] is already present.", result.getName()));
                }, () -> accountRepository.save(new AccountEntity(account.getId(), account.getName(), DEFAULT_ACCOUNT_BALANCE)));
    }

    public Account getAccount(int accountId) {

        return accountRepository.findById(accountId)
                .map(entity -> mapper.map(entity, Account.class))
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account not found with account ID. [%d]", accountId)));

        //TODO: Check what happens when account does not exist.

    }

    public void save(Account account) {
        accountRepository.save(mapper.map(account, AccountEntity.class));

        LOGGER.info("Saving account information. [{}]", account);
    }
}
