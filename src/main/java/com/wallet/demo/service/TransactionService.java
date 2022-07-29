package com.wallet.demo.service;

import com.wallet.demo.dto.Transaction;
import com.wallet.demo.dto.TransactionList;
import com.wallet.demo.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handle API calls from the Transaction controller.
 *
 * @author David Winn
 */
@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void handleTransaction(Transaction transactionRequest) {

    }

    public TransactionList getTransactions(int accountId) {
        return null;
    }
}
