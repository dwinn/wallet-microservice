package com.wallet.demo.service;

import com.wallet.demo.exception.InvalidBalanceException;
import com.wallet.demo.exception.TransactionExistsException;
import com.wallet.demo.models.Account;
import com.wallet.demo.models.Transaction;
import com.wallet.demo.models.TransactionResponse;
import com.wallet.demo.models.enums.TransactionType;
import com.wallet.demo.persistence.TransactionEntity;
import com.wallet.demo.persistence.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Handle API calls from the Transaction controller.
 *
 * @author David Winn
 */
@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public TransactionResponse handleTransaction(Transaction transactionRequest) {

        Optional<TransactionEntity> result = transactionRepository.findById(transactionRequest.getTransactionId());

        if (result.isPresent()) {
            throw new TransactionExistsException(String.format("A transaction already exists with the [%s] ID.", result.get().getId()));
        }

        Account account = accountService.getAccount(transactionRequest.getAccountId());

        double newBalance = account.getBalance();

        // The player wants to credit their account with funds.
        if (transactionRequest.getTransactionType().equals(TransactionType.CREDIT)) {
            newBalance = account.getBalance() + transactionRequest.getAmount();

            updateBalance(account, transactionRequest, newBalance);
        }

        // The player wants to debit money from their account.
        if (transactionRequest.getTransactionType().equals(TransactionType.DEBIT)) {
            newBalance = account.getBalance() - transactionRequest.getAmount();

            // A debit can only occur if it does not take the balance below 0.
            if (newBalance <= 0) {
                throw new InvalidBalanceException(String.format("Your balance cannot be negative. Your remaining balance is: [%s].", account.getBalance()));
            }

            updateBalance(account, transactionRequest, newBalance);
        }

        return (new TransactionResponse(transactionRequest.getTransactionId(), true, newBalance));

        //TODO: Test what happens with invalid transaction type. May need to handle that here.
    }

    private void updateBalance(Account account, Transaction transactionRequest, double balance) {
        account.setBalance(balance);
        accountService.save(account);
        transactionRepository.save(
            new TransactionEntity(
                    transactionRequest.getTransactionId(),
                    account.getId(),
                    transactionRequest.getAmount(),
                    transactionRequest.getTransactionType()
            )
        );
    }

    public List<Transaction> getTransactions(int accountId) {
        //TODO: Remove TransactionList class.
        return null;
    }
}
