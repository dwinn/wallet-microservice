package com.wallet.demo.controller;

import com.wallet.demo.dto.Transaction;
import com.wallet.demo.dto.TransactionList;
import com.wallet.demo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for handling calls to the transaction service.
 *
 * @author David Winn
 */
@RestController
@RequestMapping("/api")
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleTransaction(@Valid @RequestBody Transaction transaction) {
        transactionService.handleTransaction(transaction);
    }

    @GetMapping(value = "/transactions/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionList getTransactions(@PathVariable("accountId") int accountId) {
        return transactionService.getTransactions(accountId);
    }

    // Helpful for debugging bad requests in end points.
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        LOGGER.warn("Returning HTTP 400 Bad Request", e);
    }
}
