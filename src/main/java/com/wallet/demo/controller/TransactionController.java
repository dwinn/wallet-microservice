package com.wallet.demo.controller;

import com.wallet.demo.models.Transaction;
import com.wallet.demo.models.TransactionResponse;
import com.wallet.demo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for handling calls to the transaction service.
 *
 * @author David Winn
 */
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse handleTransaction(@Valid @RequestBody Transaction transaction) {
        LOGGER.info("Accepting POST /transaction with transaction [{}]", transaction);

        TransactionResponse transactionResponse = transactionService.handleTransaction(transaction);
        return transactionResponse;
    }

    @GetMapping(value = "/list/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> getTransactions(@PathVariable("accountId") int accountId) {
        LOGGER.info("Accepting GET /transactions/{accountId} with accountId [{}]", accountId);
        return transactionService.getTransactions(accountId);
    }
}
