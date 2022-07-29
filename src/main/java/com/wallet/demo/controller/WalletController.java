package com.wallet.demo.controller;

import com.wallet.demo.dto.Account;
import com.wallet.demo.dto.Transaction;
import com.wallet.demo.dto.TransactionList;
import com.wallet.demo.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for handling calls to the wallet service.
 *
 * @author David Winn
 */
@RestController
@RequestMapping("/api")
public class WalletController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAccount(@Valid @RequestBody Account account) {
        walletService.createAccount(account);
    }

    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void handleTransaction(@Valid @RequestBody Transaction transaction) {
        walletService.handleTransaction(transaction);
    }

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getBalance(@PathVariable("accountId") int accountId) {
        return walletService.getBalance(accountId);
    }

    @GetMapping(value = "/transactions/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionList getTransactions(@PathVariable("accountId") int accountId) {
        return walletService.getTransactions(accountId);
    }

    // Helpful for debugging bad requests in end points.
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        LOGGER.warn("Returning HTTP 400 Bad Request", e);
    }
}