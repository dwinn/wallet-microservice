package com.wallet.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Account(int accountId, String accountName, double balance) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
    }
}