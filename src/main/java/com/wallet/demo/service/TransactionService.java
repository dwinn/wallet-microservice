package com.wallet.demo.service;

import com.wallet.demo.exception.InvalidBalanceException;
import com.wallet.demo.exception.InvalidTransactionTypeException;
import com.wallet.demo.exception.TransactionExistsException;
import com.wallet.demo.models.Account;
import com.wallet.demo.models.Transaction;
import com.wallet.demo.models.TransactionResponse;
import com.wallet.demo.persistence.AccountEntity;
import com.wallet.demo.persistence.TransactionEntity;
import com.wallet.demo.persistence.TransactionRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handle API calls from the Transaction controller.
 *
 * @author David Winn
 */
@Service
public class TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    public static final String CREDIT = "CREDIT";
    public static final String DEBIT = "DEBIT";

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final Mapper mapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountService accountService,
                              Mapper mapper) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.mapper = mapper;
    }

    /**
     * Handle a CREDIT or DEBIT transaction to an account.
     *
     * @param transactionRequest - A request to perform a new transaction on an account.
     *
     * @return {@link TransactionResponse} - Success message containing new balance.
     */
    @Transactional
    public TransactionResponse handleTransaction(Transaction transactionRequest) {

        Optional<TransactionEntity> result = transactionRepository.findByTransactionId(transactionRequest.getTransactionId());

        // Check if this transaction ID already exists.
        if (result.isPresent()) {
            throw new TransactionExistsException(String.format("A transaction already exists with the [%s] ID.", result.get().getTransactionId()));
        }

        Account account = accountService.getAccount(transactionRequest.getAccountId());

        double newBalance = account.getBalance();

        // The player wants to credit their account with funds.
        if (transactionRequest.getTransactionType().equals(CREDIT)) {
            newBalance += transactionRequest.getAmount();

            updateBalance(account, transactionRequest, newBalance);

            LOGGER.info("The player [{}] has successfully credited their account with [??{}].", account.getName(), transactionRequest.getAmount());

            return (new TransactionResponse(transactionRequest.getTransactionId(), true, newBalance));
        }

        // The player wants to debit money from their account.
        if (transactionRequest.getTransactionType().equals(DEBIT)) {
            newBalance -= transactionRequest.getAmount();

            // A debit can only occur if it does not take the balance below 0.
            if (newBalance <= 0) {
                throw new InvalidBalanceException(String.format("Your balance cannot be negative. Your remaining balance is: [%s].", account.getBalance()));
            }

            updateBalance(account, transactionRequest, newBalance);

            LOGGER.info("The player [{}] has successfully debited their account for [??{}].", account.getName(), transactionRequest.getAmount());

            return (new TransactionResponse(transactionRequest.getTransactionId(), true, newBalance));
        }

        throw new InvalidTransactionTypeException(String.format("Unknown transaction of type [%s].", transactionRequest.getTransactionType()));
    }

    private void updateBalance(Account account, Transaction transactionRequest, double balance) {
        // Update the account balance.
        accountService.save(
            new AccountEntity(
                    account.getId(),
                    account.getName(),
                    balance
            )
        );

        // Log the transaction.
        transactionRepository.save(
            new TransactionEntity(
                    transactionRequest.getTransactionId(),
                    account.getId(),
                    transactionRequest.getAmount(),
                    transactionRequest.getTransactionType()
            )
        );
    }

    /**
     * Get all the transactions for a given account ID.
     *
     * @param accountId The given account ID.
     *
     * @return A list of {@link Transaction} containing all transactions for an account.
     */
    @Transactional(readOnly = true)
    public List<Transaction> getTransactions(int accountId) {
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(entity -> mapper.map(entity, Transaction.class))
                .collect(Collectors.toList());
    }
}
