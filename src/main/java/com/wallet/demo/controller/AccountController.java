package com.wallet.demo.controller;

import com.wallet.demo.dto.Account;
import com.wallet.demo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for handling calls to the account service.
 *
 * @author David Winn
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAccount(@Valid @RequestBody Account account) {
        accountService.createAccount(account);
    }

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getBalance(@PathVariable("accountId") int accountId) {
        return accountService.getBalance(accountId);
    }

    // Helpful for debugging bad requests in end points.
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        LOGGER.warn("Returning HTTP 400 Bad Request", e);
    }
}