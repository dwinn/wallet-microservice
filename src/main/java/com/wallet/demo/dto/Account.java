package com.wallet.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.demo.persistence.AccountEntity;

/**
 * A DTO describing an account.
 *
 * @author David Winn
 */
public class Account {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private double balance;

    public Account(AccountEntity account) {
        this.id = account.getId();
        this.name = account.getName();
        this.balance = account.getBalance();
    }

    public int getId() {
        return id;
    }

    public void setId(int accountId) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}