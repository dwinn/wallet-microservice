package com.wallet.demo.service;

import com.wallet.demo.dto.Account;
import com.wallet.demo.exception.AccountNotFoundException;
import com.wallet.demo.persistence.AccountEntity;
import com.wallet.demo.persistence.AccountRepository;
import org.dozer.Mapper;
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
    private final Mapper mapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, Mapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    public void createAccount(Account account) {

    }

    public Account getBalance(int accountId) {
        return accountRepository.findByAccountId(accountId)
                .map(entity -> mapper.map(entity, Account.class))
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account not founf with account ID. [%d]", accountId)));
    }
}
