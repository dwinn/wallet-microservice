package com.wallet.demo.controller;

import com.wallet.demo.models.Account;
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
@RequestMapping("/api/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody Account account) {
        LOGGER.info("Accepting POST /account with account [{}]", account);

        accountService.createAccount(account);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getAccount(@PathVariable("id") int id) {
        LOGGER.info("Accepting GET /account/{id} with ID [{}]", id);

        return accountService.getAccount(id);
    }
}
