package com.wallet.demo.service;

import com.wallet.demo.dto.Account;
import com.wallet.demo.dto.Transaction;
import com.wallet.demo.dto.TransactionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Handle API calls from the Wallet controller.
 *
 * @author David Winn
 */
@Service
public class WalletService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalletService.class);

    public void createAccount(Account account) {

    }

    public void handleTransaction(Transaction transactionRequest) {

    }

    public TransactionList getTransactions(int accountId) {
        return null;
    }

    public Account getBalance(int accountId) {
        return null;
    }
}