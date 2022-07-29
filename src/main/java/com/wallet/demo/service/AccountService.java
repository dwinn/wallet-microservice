package com.wallet.demo.service;

import com.wallet.demo.dto.Account;
import com.wallet.demo.persistence.AccountEntity;
import com.wallet.demo.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Handle API calls from the Wallet controller.
 *
 * @author David Winn
 */
@Service
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account) {

    }

    public Account getBalance(int accountId) {

        Optional<AccountEntity> accountEntity = accountRepository.findByAccountId(accountId);

        return accountEntity.map(entity -> new Account(accountEntity.get())).orElse(null);

        // TODO: Change the ELSE to a more friendly output. Maybe an error message.
    }
}