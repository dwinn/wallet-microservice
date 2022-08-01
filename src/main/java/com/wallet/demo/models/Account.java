package com.wallet.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wallet.demo.persistence.AccountEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * A DTO describing an account.
 *
 * @author David Winn
 */
public class Account {

    @NotNull
    @JsonProperty("id")
    private int id;

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("balance")
    private double balance;

    public Account(AccountEntity account) {
        this.id = account.getId();
        this.name = account.getName();
        this.balance = account.getBalance();
    }

    public Account() {
        // Default constructor for hibernate.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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