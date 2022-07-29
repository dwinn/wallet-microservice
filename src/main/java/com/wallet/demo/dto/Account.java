package com.wallet.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.demo.persistence.AccountEntity;

/**
 * A DTO describing an account.
 *
 * @author David Winn
 */
public class Account {

    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("balance")
    private double balance;

    public Account(AccountEntity account) {
        this.accountId = account.getAccountId();
        this.accountName = account.getAccountName();
        this.balance = account.getBalance();
    }
}